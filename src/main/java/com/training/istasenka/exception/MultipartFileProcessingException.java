package com.training.istasenka.exception;

import org.springframework.core.NestedRuntimeException;

public class MultipartFileProcessingException extends NestedRuntimeException {
    public MultipartFileProcessingException(String message) {
        super(message);
    }
}
