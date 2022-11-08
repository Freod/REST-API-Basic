package com.epam.esm.errors;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceViolationException;
import com.epam.esm.exception.WrongPageException;
import com.epam.esm.exception.WrongValueException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static Logger logger = LogManager.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(Exception.class)
    public @ResponseBody String handleUnknownExceptionAndLog(Exception exception) {
        logger.error(exception);
        return "Unexpected error occurred, please contact: example@mail.com";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public @ResponseBody ErrorInfo handleResourceNotFoundException(ResourceNotFoundException exception) {
        logger.info(exception);
        return new ErrorInfo(ErrorCode.RESOURCE_NOT_FOUND.getCode(), exception.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceViolationException.class)
    public @ResponseBody ErrorInfo handleResourceViolationException(ResourceViolationException exception) {
        logger.info(exception);
        return new ErrorInfo(ErrorCode.RESOURCE_VIOLATION.getCode(), exception.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(WrongPageException.class)
    public @ResponseBody ErrorInfo handleWrongPageException(WrongPageException exception) {
        logger.info(exception);
        return new ErrorInfo(ErrorCode.WRONG_PAGE.getCode(), exception.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(WrongValueException.class)
    public @ResponseBody ErrorInfo handleWrongValueException(WrongValueException exception) {
        logger.info(exception);
        return new ErrorInfo(ErrorCode.WRONG_VALUE.getCode(), exception.getLocalizedMessage());
    }
}
