package com.epam.esm.errors;

import java.util.Objects;

public class ErrorInfo {
    public final int errorCode;
    public final String errorMessage;

    public ErrorInfo(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = Objects.requireNonNull(errorMessage);
    }
}
