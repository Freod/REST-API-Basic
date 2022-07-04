package com.epam.esm.errors;

import java.util.Objects;

public class ErrorInfo {
    public final String errorMessage;
    public final String errorCode;

    public ErrorInfo(String errorCode, Exception exception) {
        this.errorCode = Objects.requireNonNull(errorCode);
        this.errorMessage = Objects.requireNonNull(exception.getLocalizedMessage());
    }
}
