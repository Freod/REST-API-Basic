package com.epam.esm.errors;

import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.exception.ResourceViolation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFound.class)
    public @ResponseBody ErrorInfo handleResourceNotFound(ResourceNotFound exception) {
        return new ErrorInfo("40401", exception);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceViolation.class)
    public @ResponseBody ErrorInfo handleResourceNotFound(ResourceViolation exception) {
        return new ErrorInfo("40402", exception);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(Exception.class)
    public @ResponseBody ErrorInfo handleResourceNotFound(Exception exception) {
        return new ErrorInfo("unknown", exception);
    }
}
