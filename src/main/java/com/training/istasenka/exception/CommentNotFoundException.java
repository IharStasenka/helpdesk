package com.training.istasenka.exception;

import java.util.NoSuchElementException;

public class CommentNotFoundException extends NoSuchElementException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
