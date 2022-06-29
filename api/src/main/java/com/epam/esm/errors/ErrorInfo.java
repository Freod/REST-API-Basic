package com.epam.esm.errors;

public class ErrorInfo {
    public final String errorMessage;
    public final String errorCode;

    public ErrorInfo(String errorCode, Exception exception) {
        this.errorCode = errorCode;
        this.errorMessage = exception.getLocalizedMessage();
    }
}
