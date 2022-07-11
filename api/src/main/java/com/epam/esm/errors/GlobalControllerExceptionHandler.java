package com.epam.esm.errors;

import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.exception.ResourceViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Logger;

@ControllerAdvice
class GlobalControllerExceptionHandler {

    private Logger LOGGER = Logger.getGlobal();

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(Exception.class)
    public @ResponseBody ErrorInfo handleUnknownExceptionAndLog(Exception exception) {
        LOGGER.warning(exception.getLocalizedMessage());
        return new ErrorInfo(ErrorCode.UNKNOWN.getCode(), "Something unexpected happened");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFound.class)
    public @ResponseBody ErrorInfo handleResourceNotFound(ResourceNotFound exception) {
        return new ErrorInfo(ErrorCode.RESOURCE_NOT_FOUND.getCode(), exception.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceViolation.class)
    public @ResponseBody ErrorInfo handleResourceViolation(ResourceViolation exception) {
        return new ErrorInfo(ErrorCode.RESOURCE_VIOLATION.getCode(), exception.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @ResponseBody ErrorInfo handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        LOGGER.warning(exception.getLocalizedMessage());
        return new ErrorInfo(ErrorCode.HTTP_MESSAGE_NOT_READABLE.getCode(), "Something wrong with request (maybe required request body is missing)");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NullPointerException.class)
    public @ResponseBody ErrorInfo handleNullPointerException(NullPointerException exception) {
        return new ErrorInfo(ErrorCode.NULL_POINTER.getCode(), exception.getLocalizedMessage());
    }
}
