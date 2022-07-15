package com.training.istasenka.exception;

import java.util.NoSuchElementException;

public class AttachmentNotFoundException extends NoSuchElementException {
    public AttachmentNotFoundException(String message) {
        super(message);
    }
}
