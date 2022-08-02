package com.training.istasenka.service.comment;

import com.training.istasenka.exception.CommentNotFoundException;
import com.training.istasenka.exception.CustomIllegalArgumentException;
import com.training.istasenka.model.comment.Comment;
import com.training.istasenka.model.user.User;
import com.training.istasenka.repository.comment.CommentRepository;
import com.training.istasenka.provider.specification.pageable.pageable.PaginationProviderImpl;
import com.training.istasenka.provider.specification.comment.CommentSpecificationProvider;
import com.training.istasenka.service.ticket.TicketService;
import com.training.istasenka.service.user.db.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final TicketService ticketService;
    private final CommentRepository commentRepository;
    private final CommentSpecificationProvider commentSpecificationProvider;
    private final UserService userService;
    private final PaginationProviderImpl paginationProviderImpl;

    @Override
    @Transactional
    public Comment findCommentById(Long commentId, Long ticketId) {
        validateTicketResourceById(ticketId);
        Comment cachedComment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        isConsistentTicketId(cachedComment, commentId, ticketId);
        return cachedComment;
    }

    @Override
    @Transactional
    public void updateCommentById(Comment comment, long commentId, Long ticketId) {
        Comment cachedComment = findCommentById(commentId, ticketId);
        if (!getUserFromSecurityContext().getEmail().equals(cachedComment.getUser().getEmail())) {
            throw new CustomIllegalArgumentException(String.format("That context user cant change comment with id %d", commentId));
        }
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteCommentById(Long commentId, Long ticketId) {
        findCommentById(commentId, ticketId);
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public Long saveComment(Comment comment, Long ticketId) {
        validateTicketResourceById(ticketId);
        var ticket = ticketService.getTicketById(ticketId);
        comment.setTicket(ticket);
        comment.setUser(getUserFromSecurityContext());
        ticket.addComment(comment);
        return commentRepository.save(comment).getId();
    }

    @Override
    @Transactional
    public Page<Comment> findPageOfCommentsForTicket(Long ticketId, Pageable pageable) {
        validateTicketResourceById(ticketId);
        return commentRepository
                .findAll(commentSpecificationProvider.getCommentBySpecificationProvider(ticketId), pageable);
    }

    private void validateTicketResourceById(Long ticketId) {
        ticketService.validateTicketResourceById(ticketId);
    }

    private Boolean isConsistentTicketId(Comment comment, long commentId, Long ticketId) {
        if (!Objects.equals(ticketId, comment.getTicket().getId())) {
            throw new CustomIllegalArgumentException(String.format("There are no comment with id %d for ticket with id %d", commentId, ticketId));
        } else {
            return true;
        }
    }

    private User getUserFromSecurityContext() {
        return userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
