package com.epam.esm.errors;

public enum ErrorCode {
    RESOURCE_NOT_FOUND(40401),
    RESOURCE_VIOLATION(40402),
    NULL_POINTER(40403),
    HTTP_MESSAGE_NOT_READABLE(40404);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
