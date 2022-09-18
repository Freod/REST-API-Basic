package com.epam.esm.exception;

public class WrongValueException extends RuntimeException {
    public WrongValueException(String message) {
        super(message);
    }
}
