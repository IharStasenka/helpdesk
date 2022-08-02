package com.training.istasenka.exception;

import java.util.NoSuchElementException;

public class AttachmentNotFoundException extends NoSuchElementException {
    public AttachmentNotFoundException(Long attachmentId) {
        super(String.format("There are no attachment with id % d", attachmentId));
    }
}
