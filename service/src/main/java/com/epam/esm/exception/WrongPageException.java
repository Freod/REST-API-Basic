package com.epam.esm.exception;

public class WrongPageException extends RuntimeException {
    public WrongPageException(String message) {
        super(message);
    }
}
