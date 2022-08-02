package com.training.istasenka.service.ticket;

import com.training.istasenka.annotation.HistoryAudit;
import com.training.istasenka.annotation.MailAudit;
import com.training.istasenka.exception.CustomIllegalArgumentException;
import com.training.istasenka.exception.IllegalTicketStatusTransitionException;
import com.training.istasenka.exception.TicketNotFoundException;
import com.training.istasenka.model.attachment.Attachment;
import com.training.istasenka.model.category.Category;
import com.training.istasenka.model.comment.Comment;
import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.user.User;
import com.training.istasenka.provider.specification.ticket.role.UserRoleSpecificationProviders;
import com.training.istasenka.repository.specification.ticket.FilterTicketSpecification;
import com.training.istasenka.repository.specification.ticket.TicketIdSpecification;
import com.training.istasenka.repository.ticket.TicketRepository;
import com.training.istasenka.service.category.CategoryService;
import com.training.istasenka.service.user.db.UserService;
import com.training.istasenka.util.StatusType;
import com.training.istasenka.util.TicketActionType;
import com.training.istasenka.util.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.training.istasenka.util.StatusType.DRAFT;
import static com.training.istasenka.util.TicketActionType.*;
import static com.training.istasenka.util.UserRole.*;

@Service
@AllArgsConstructor
public class  TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final UserRoleSpecificationProviders userRoleSpecificationProviders;
    private final CategoryService categoryService;

    @Override
    @Transactional
    @Cacheable(cacheNames = "cache.tickets", key = "#id.toString().concat('-').concat(@contextUsernameProvider.getUsername())")
    public Boolean validateTicketResourceById(Long id) {
        ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
        validateContextUserForTicketById(id);
        return true;
    }

    @Override
    public void validateTicketResourceByIdByEmail(Long ticketId, String email) {
        if (isNotAccessibleUserForTicketById(ticketId, userService.getUser(email))) {
            throw new AccessDeniedException(String.format("Current user is not accessible for ticket with id %d", ticketId));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ticket> getAllTickets(Pageable pagination, String filterBy, Boolean myTicketFilterStatus) {
        User contextUser = this.getUserFromSecurityContext();
        var roleSpecification = getContextUserByRoleSpecification(myTicketFilterStatus, contextUser);
        roleSpecification = getFilteredUserSpecification(filterBy, roleSpecification);
        return ticketRepository
                .findAll(roleSpecification, pagination);

    }

    @Override
    @Transactional
    public Ticket getTicketById(Long ticketId) {
        validateContextUserForTicketById(ticketId);
        return ticketRepository
                .findOne(getTicketByIdSpecification(ticketId))
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
    }


    @Override
    @Transactional
    public Ticket getTicketByIdByEmail(Long ticketId, String email) {
        validateTicketResourceByIdByEmail(ticketId, email);
        return ticketRepository
                .findOne(getTicketByIdSpecification(ticketId))
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
    }

    @Override
    @Transactional
    @HistoryAudit
    public Long saveTicket(Ticket ticket, List<Attachment> attachments) {
        var owner = getUserFromSecurityContext();
        processingSavingEntity(ticket, owner, attachments, ticket.getComments(), ticket.getCategory());
        return ticketRepository.save(ticket).getId();
    }

    @Override
    @Transactional
    @HistoryAudit
    @CacheEvict(cacheNames = "cache.tickets", key = "#id.toString().concat('-').concat(@contextUsernameProvider.getUsername())")
    public void updateTicket(Ticket ticket, List<Attachment> attachments, Long id) {
        if (!id.equals(ticket.getId())) {
            throw new CustomIllegalArgumentException("Mismatch of ticket id, and id in path variable");
        }
        var cachedTicket = getTicketById(id);
        if (cachedTicket.getStatus() != DRAFT) {
            throw new CustomIllegalArgumentException("Can update ticket only in draft status");
        }
        cachedTicket.setUrgency(ticket.getUrgency());
        cachedTicket.setDescription(ticket.getDescription());
        cachedTicket.setDesiredResolutionDate(ticket.getDesiredResolutionDate());
        var owner = getUserFromSecurityContext();
        processingSavingEntity(cachedTicket, owner, attachments, ticket.getComments(), ticket.getCategory());
        ticketRepository.save(cachedTicket);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "cache.tickets", key = "#id.toString().concat('-').concat(@contextUsernameProvider.getUsername())")
    public void deleteTicket(Long id) {
        Ticket ticket = getTicketById(id);
        ticketRepository.delete(ticket);
    }

    @Override
    @Transactional
    @HistoryAudit
    @MailAudit
    @CacheEvict(cacheNames = "cache.tickets", key = "#id.toString().concat('-').concat(@contextUsernameProvider.getUsername())")
    public void updateTicketStatus(Long id, TicketActionType action) {
        var ticket = getTicketById(id);
        var contextUser = getUserFromSecurityContext();
        UserRole userRole = contextUser.getRole();
        if (isAppropriateActionForRole(action, userRole) && isAppropriateActionForStatus(action, ticket.getStatus())) {
            if (action == CANCEL && userRole == MANAGER && !ticket.getOwner().equals(contextUser)) {
                throw new IllegalTicketStatusTransitionException("This action is not appropriate for current user or current ticket status");
            }
            setTicketAdjustedUsers(action, ticket, contextUser, userRole);
            ticket.setStatus(action.getNewStatus());
        } else {
            throw new IllegalTicketStatusTransitionException("This action is not appropriate for current user or current ticket status");
        }
    }


    private void validateContextUserForTicketById(Long id) {
        if (isNotAccessibleUserForTicketById(id, this.getUserFromSecurityContext())) {
            throw new AccessDeniedException(String.format("Current user is not accessible for ticket with id %d", id));
        }
    }

    private boolean isNotAccessibleUserForTicketById(Long ticketId, User contextUser) {
        return ticketRepository
                .findAll(getContextUserByRoleSpecification(false, contextUser))
                .stream()
                .map(Ticket::getId)
                .noneMatch(id -> id.equals(ticketId));
    }

    private void setTicketAdjustedUsers(TicketActionType action, Ticket ticket, User contextUser, UserRole userRole) {
        if ((userRole == EMPLOYEE || userRole == MANAGER) && action == SUBMIT) {
            ticket.setOwner(contextUser);
        }
        if ((userRole == MANAGER) && (action == APPROVE || action == DECLINE || action == CANCEL)) {
            ticket.setApprover(contextUser);
        }
        if (userRole == ENGINEER && (action == ASSIGN_TO_ME || action == CANCEL)) {
            ticket.setAssignee(contextUser);
        }
    }

    private void processingSavingEntity(Ticket ticket, User owner, List<Attachment> attachments, List<Comment> comments, Category category) {
        ticket.setOwner(owner);
        ticket.setCategory(categoryService.getCategoryByName(category.getName()));
        ticket.addAttachments(attachments);
        var updatedComments = updateComments(comments, ticket, owner);
        ticket.addComments(updatedComments);
    }

    private boolean isAppropriateActionForStatus(TicketActionType action, StatusType currentStatus) {
        return Arrays.asList(action.getCurrentStatuses()).contains(currentStatus);
    }

    private boolean isAppropriateActionForRole(TicketActionType action, UserRole contextUserRole) {
        return Arrays.asList(contextUserRole.getActions()).contains(action);
    }

    private User getUserFromSecurityContext() {
        return userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private List<Comment> updateComments(List<Comment> comments, Ticket ticket, User owner) {
        return comments
                .stream()
                .map(comment -> {
                    comment.setTicket(ticket);
                    comment.setUser(owner);
                    return comment;
                })
                .collect(Collectors.toList());
    }

    private Specification<Ticket> getFilteredUserSpecification(String filterBy, Specification<Ticket> roleSpecification) {
        if (!filterBy.isEmpty()) {
            Specification<Ticket> filterSpecification = new FilterTicketSpecification(filterBy);
            roleSpecification = roleSpecification.and(filterSpecification);
        }
        return roleSpecification;
    }

    private Specification<Ticket> getTicketByIdSpecification(Long id) {
        return new TicketIdSpecification(id);
    }

    private Specification<Ticket> getContextUserByRoleSpecification(Boolean myTicketFilterStatus, User contextUser) {

        return userRoleSpecificationProviders.getUserRoleSpecificationProviders()
                .get(contextUser.getRole())
                .getSpecificationForFindAllTickets(contextUser.getEmail(), myTicketFilterStatus);
    }
}
