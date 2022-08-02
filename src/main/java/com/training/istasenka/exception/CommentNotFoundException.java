package com.training.istasenka.exception;

import java.util.NoSuchElementException;

public class CommentNotFoundException extends NoSuchElementException {
    public CommentNotFoundException(Long commentId) {
        super(String.format("No such comment with id %d", commentId));
    }
}
