package com.training.istasenka.repository.specification.ticket;

import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.model.ticket.Ticket_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TicketIdSpecification implements Specification<Ticket> {

    private final Long id;

    public TicketIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public Predicate toPredicate(Root<Ticket> root, @NonNull CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(Ticket_.id), id);
    }
}
