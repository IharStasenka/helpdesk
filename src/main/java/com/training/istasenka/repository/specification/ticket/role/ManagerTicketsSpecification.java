package com.training.istasenka.repository.specification.ticket.role;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.ticket.Ticket_;
import com.training.istasenka.model.user.User;
import com.training.istasenka.model.user.User_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.*;

import static com.training.istasenka.util.StatusType.APPROVED;
import static com.training.istasenka.util.StatusType.NEW;
import static com.training.istasenka.util.UserRole.EMPLOYEE;

public class ManagerTicketsSpecification implements Specification<Ticket> {

    private final String username;
    private final Boolean myTicketFilterStatus;

    public ManagerTicketsSpecification(String username, Boolean myTicketFilterStatus) {
        this.username = username;
        this.myTicketFilterStatus = myTicketFilterStatus;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Ticket> root, @NonNull CriteriaQuery<?> criteriaQuery, @NonNull CriteriaBuilder criteriaBuilder) {

        return getPredicateForManagerTickets(root, criteriaBuilder);
    }

    private Predicate getPredicateForManagerTickets(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {

        Join<Ticket, User> ownerJoin = root.join(Ticket_.owner, JoinType.LEFT);
        Join<Ticket, User> approverJoin = root.join(Ticket_.approver, JoinType.LEFT);

        if (Boolean.TRUE.equals(myTicketFilterStatus)) {
            return criteriaBuilder.or(
                    getPredicateForMyApproved(root, criteriaBuilder, approverJoin),
                    getPredicateForOwner(criteriaBuilder, ownerJoin)
            );
        } else {
            return criteriaBuilder.or(
                    getPredicateForMyApprovedInAllStatus(root, criteriaBuilder, approverJoin),
                    getPredicateForOwner(criteriaBuilder, ownerJoin),
                    getPredicateForNewTickets(root, criteriaBuilder, ownerJoin)
            );
        }
    }

    private Predicate getPredicateForNewTickets(Root<Ticket> root, CriteriaBuilder criteriaBuilder, Join<Ticket, User> ownerJoin) {

        return criteriaBuilder.and(
                criteriaBuilder.equal(ownerJoin.get(User_.role), EMPLOYEE),
                criteriaBuilder.equal(root.get(Ticket_.status), NEW)
        );
    }

    private Predicate getPredicateForMyApproved(Root<Ticket> root, CriteriaBuilder criteriaBuilder, Join<Ticket, User> approverJoin) {

        return criteriaBuilder.and(
                criteriaBuilder.equal(approverJoin.get(User_.email), username),
                criteriaBuilder.equal(root.get(Ticket_.status), APPROVED)
        );
    }


    private Predicate getPredicateForMyApprovedInAllStatus(Root<Ticket> root, CriteriaBuilder criteriaBuilder, Join<Ticket, User> approverJoin) {

        return criteriaBuilder.and(
                criteriaBuilder.equal(approverJoin.get(User_.email), username),
                criteriaBuilder.greaterThanOrEqualTo(root.get(Ticket_.status), APPROVED)
        );
    }

    private Predicate getPredicateForOwner(CriteriaBuilder criteriaBuilder, Join<Ticket, User> ownerJoin) {

        return criteriaBuilder.equal(
                ownerJoin.get(User_.email), username
        );
    }
}
