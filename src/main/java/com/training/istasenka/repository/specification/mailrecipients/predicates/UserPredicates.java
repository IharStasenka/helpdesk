package com.training.istasenka.repository.specification.mailrecipients.predicates;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.ticket.Ticket_;
import com.training.istasenka.model.user.User;
import com.training.istasenka.model.user.User_;

import javax.persistence.criteria.*;

import static com.training.istasenka.util.UserRole.*;

public class UserPredicates {
    public Predicate getAllManagers(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(User_.ROLE), MANAGER);
    }

    public Predicate getAllEngineers(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(User_.ROLE), ENGINEER);
    }

    public Predicate getAssignee(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, Long ticketId) {
        var ticketRoot = query.from(Ticket.class);
        return criteriaBuilder.and(
                criteriaBuilder.equal(ticketRoot.get(Ticket_.id), ticketId),
                criteriaBuilder.equal(ticketRoot.get(Ticket_.assignee).get(User_.userId), root.get(User_.userId))
        );
    }

    public Predicate getOwner(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, Long ticketId) {
        var ticketRoot = query.from(Ticket.class);
        return criteriaBuilder.and(
                criteriaBuilder.equal(ticketRoot.get(Ticket_.id), ticketId),
                criteriaBuilder.equal(ticketRoot.get(Ticket_.owner).get(User_.userId), root.get(User_.userId))
        );
    }

    public Predicate getApprover(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, Long ticketId) {
        var ticketRoot = query.from(Ticket.class);
        return criteriaBuilder.and(
                criteriaBuilder.equal(ticketRoot.get(Ticket_.id), ticketId),
                criteriaBuilder.equal(ticketRoot.get(Ticket_.approver).get(User_.userId), root.get(User_.userId))
        );
    }
}
