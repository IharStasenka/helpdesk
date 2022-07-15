package com.training.istasenka.repository.specification.ticket.role;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.ticket.Ticket_;
import com.training.istasenka.model.user.User;
import com.training.istasenka.model.user.User_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.*;

import static com.training.istasenka.util.StatusType.*;
import static com.training.istasenka.util.UserRole.EMPLOYEE;
import static com.training.istasenka.util.UserRole.MANAGER;
import static javax.persistence.criteria.JoinType.LEFT;

public class EngineerTicketsSpecification implements Specification<Ticket> {
    private final String username;
    private final Boolean myTicketFilterStatus;

    public EngineerTicketsSpecification(String username, Boolean myTicketFilterStatus) {
        this.username = username;
        this.myTicketFilterStatus = myTicketFilterStatus;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Ticket>  root, @NonNull CriteriaQuery<?> criteriaQuery, @NonNull CriteriaBuilder criteriaBuilder) {
        return getPredicateForEngineer(root, criteriaBuilder);
    }

    private Predicate getPredicateForEngineer(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {

        Join<Ticket, User> assigneeJoin = root.join(Ticket_.assignee, LEFT);
        Join<Ticket, User> ownerJoin = root.join(Ticket_.owner, LEFT);

        if (Boolean.FALSE.equals(myTicketFilterStatus)) {
            return criteriaBuilder.or(
                    getPredicateForApprovedTickets(root, criteriaBuilder, ownerJoin),
                    getPredicateForAssignee(root, criteriaBuilder, assigneeJoin)
            );
        } else {
            return getPredicateForMyAssignee(criteriaBuilder, assigneeJoin);

        }
    }

    private Predicate getPredicateForMyAssignee(CriteriaBuilder criteriaBuilder, Join<Ticket, User> assigneeJoin) {
        return criteriaBuilder.equal(assigneeJoin.get(User_.email), username);
    }

    private Predicate getPredicateForAssignee(Root<Ticket> root, CriteriaBuilder criteriaBuilder, Join<Ticket, User> assigneeJoin) {
        return criteriaBuilder.and(
                criteriaBuilder.equal(assigneeJoin.get(User_.email), username),
                criteriaBuilder.or(
                        criteriaBuilder.equal(root.get(Ticket_.status), IN_PROGRESS),
                        criteriaBuilder.equal(root.get(Ticket_.status), DONE)
                )
        );
    }

    private Predicate getPredicateForApprovedTickets(Root<Ticket> root, CriteriaBuilder criteriaBuilder, Join<Ticket, User> ownerJoin) {
        return criteriaBuilder.and(
                criteriaBuilder.or(
                        criteriaBuilder.equal(ownerJoin.get(User_.role), MANAGER),
                        criteriaBuilder.equal(ownerJoin.get(User_.role), EMPLOYEE)),
                criteriaBuilder.equal(root.get(Ticket_.status), APPROVED)
        );
    }
}
