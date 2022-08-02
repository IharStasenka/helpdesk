package com.training.istasenka.repository.specification.comment;

import com.training.istasenka.model.comment.Comment;
import com.training.istasenka.model.comment.Comment_;
import com.training.istasenka.model.ticket.Ticket_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CommentsByTicketIdSpecification implements Specification<Comment> {

    private final Long ticketId;

    public CommentsByTicketIdSpecification(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public Predicate toPredicate(Root<Comment> root, @NonNull CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(Comment_.ticket).get(Ticket_.id), ticketId);
    }
}
