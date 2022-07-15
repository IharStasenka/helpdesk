package com.training.istasenka.service.comment;

import com.training.istasenka.model.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Comment findCommentById(Long commentId, Long ticketId);

    void updateCommentById(Comment comment, long commentId, Long ticketId);

    void deleteCommentById(Long commentId, Long ticketId);

    Long saveComment(Comment comment, Long ticketId);

    Page<Comment> findPageOfCommentsForTicket(Long ticketId, Pageable pageable);
}
