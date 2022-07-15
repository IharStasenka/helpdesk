package com.training.istasenka.exception;

import java.util.NoSuchElementException;

public class FeedbackNotFoundException extends NoSuchElementException {
    public FeedbackNotFoundException(String message) {
        super(message);
    }
}
