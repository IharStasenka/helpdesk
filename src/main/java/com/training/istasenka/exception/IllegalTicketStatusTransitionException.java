package com.training.istasenka.exception;

public class IllegalTicketStatusTransitionException extends IllegalArgumentException {
    public IllegalTicketStatusTransitionException(String message) {
        super(message);
    }
}
