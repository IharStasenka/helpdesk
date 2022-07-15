package com.training.istasenka.provider.specification.comment;

import com.training.istasenka.repository.specification.comment.CommentsByTicketIdSpecification;
import org.springframework.stereotype.Component;

@Component
public class CommentSpecificationProviderImpl implements CommentSpecificationProvider {

    @Override
    public CommentsByTicketIdSpecification getCommentBySpecificationProvider(Long ticketId) {
        return new CommentsByTicketIdSpecification(ticketId);
    }
}
