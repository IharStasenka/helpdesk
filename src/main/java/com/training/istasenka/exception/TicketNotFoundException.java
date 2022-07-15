package com.training.istasenka.exception;

import java.util.NoSuchElementException;

public class TicketNotFoundException extends NoSuchElementException {
    public TicketNotFoundException(String message) {
        super(message);
    }
}
