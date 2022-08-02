package com.training.istasenka.exception;

import java.util.NoSuchElementException;

public class TicketNotFoundException extends NoSuchElementException {
    public TicketNotFoundException(Long ticketId) {
        super(String.format("There are no ticket with id: %d", ticketId));
    }
}
