package com.training.istasenka.provider.specification.comment;

import com.training.istasenka.repository.specification.comment.CommentsByTicketIdSpecification;

public interface CommentSpecificationProvider {

    CommentsByTicketIdSpecification getCommentBySpecificationProvider(Long ticketId);

}
