package com.training.istasenka.repository.specification.mailrecipients;

import com.training.istasenka.model.user.User;
import com.training.istasenka.repository.specification.mailrecipients.predicates.UserPredicates;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TemplateSixSpecification implements Specification<User> {

    private final UserPredicates userPredicates = new UserPredicates();

    private final Long ticketId;

    public TemplateSixSpecification(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<User> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        return userPredicates.getOwner(root, query, criteriaBuilder, ticketId);
    }

}
