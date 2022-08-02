package com.training.istasenka.repository.specification.attachment;

import com.training.istasenka.model.attachment.Attachment;
import com.training.istasenka.model.attachment.Attachment_;
import com.training.istasenka.model.ticket.Ticket_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AttachmentSpecification implements Specification<Attachment> {

    private final Long ticketId;

    public AttachmentSpecification(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public Predicate toPredicate(Root<Attachment> root, @NonNull CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(Attachment_.ticket).get(Ticket_.id), ticketId);
    }
}
