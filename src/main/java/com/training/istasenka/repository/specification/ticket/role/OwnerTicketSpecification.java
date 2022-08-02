package com.training.istasenka.repository.specification.ticket.role;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.ticket.Ticket_;
import com.training.istasenka.model.user.User_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class OwnerTicketSpecification implements Specification<Ticket> {

    private final String username;
    private final Boolean myTicketFilterStatus;

    public OwnerTicketSpecification(String username, Boolean myTicketFilterStatus) {
        this.username = username;
        this.myTicketFilterStatus = myTicketFilterStatus;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Ticket> root, @NonNull CriteriaQuery<?> criteriaQuery, @NonNull CriteriaBuilder criteriaBuilder) {
        return getPredicateForOwner(root, criteriaBuilder);

    }

    private Predicate getPredicateForOwner(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(
                root.get(Ticket_.owner).get(User_.email), username
        );
    }
}
