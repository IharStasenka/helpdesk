package com.training.istasenka.repository.specification.ticket;

import com.fasterxml.jackson.annotation.JsonView;
import com.training.istasenka.dto.ticket.TicketDto;
import com.training.istasenka.dto.ticket.View;
import com.training.istasenka.model.ticket.Ticket;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;

public class FilterTicketSpecification implements Specification<Ticket> {
    private final String filterBy;

    public FilterTicketSpecification(String filterBy) {
        this.filterBy = filterBy;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Ticket> root, @NonNull CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate[] predicateList = Arrays.stream(TicketDto.class.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(JsonView.class))
                .filter(field -> Arrays.asList(field.getAnnotation(JsonView.class).value()).contains(View.ShortTicket.class))
                .map(field -> criteriaBuilder
                        .like(
                                criteriaBuilder.lower(
                                        root.get(field.getName()).as(String.class)), "%" + filterBy.toLowerCase() + "%"
                        )
                )
                .toArray(Predicate[]::new);
        return criteriaBuilder.or(predicateList);
    }
}
