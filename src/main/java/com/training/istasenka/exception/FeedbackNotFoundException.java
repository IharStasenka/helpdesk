package com.training.istasenka.exception;

import java.util.NoSuchElementException;

public class FeedbackNotFoundException extends NoSuchElementException {
    public FeedbackNotFoundException(Long feedbackId) {
        super(String.format("No such feedback with id %d", feedbackId));
    }
}
