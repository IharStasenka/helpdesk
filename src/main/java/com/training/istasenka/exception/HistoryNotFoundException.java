package com.training.istasenka.exception;

import java.util.NoSuchElementException;

public class HistoryNotFoundException extends NoSuchElementException {
    public HistoryNotFoundException(Long ticketId) {
        super(String.format("There are no history with id: %d", ticketId));
    }
}
