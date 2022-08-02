package com.training.istasenka.repository.specification.history;

import com.training.istasenka.model.history.History;
import com.training.istasenka.model.history.History_;
import com.training.istasenka.model.ticket.Ticket_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class HistoriesByTicketIdSpecification implements Specification<History> {

    private final Long ticketId;

    public HistoriesByTicketIdSpecification(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public Predicate toPredicate(Root<History> root, @NonNull CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(History_.ticket).get(Ticket_.id), ticketId);
    }
}
