package com.epam.esm.exception;

public class ResourceViolationException extends RuntimeException {
    public ResourceViolationException(String message) {
        super(message);
    }
}
