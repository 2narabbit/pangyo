package com.adinstar.pangyo.controller.adviser;


import com.adinstar.pangyo.controller.exception.BadRequestException;
import com.adinstar.pangyo.controller.exception.InvalidConditionException;
import com.adinstar.pangyo.controller.exception.NotFoundException;
import com.adinstar.pangyo.controller.exception.UnauthorizedException;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.ServiceUnavailableException;

@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler {

    @ExceptionHandler({
            BadRequestException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            ServletRequestBindingException.class,
            HttpRequestMethodNotSupportedException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResult handleBadRequestException(Exception e) {
        return new ErrorResult(e);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResult handleNotFoundException(NotFoundException e) {
        return new ErrorResult(e);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Object handleUnauthorizedException(UnauthorizedException e) {
        return new ErrorResult(e);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResult handleServiceUnavailableException(ServiceUnavailableException e) {
        return new ErrorResult(e);
    }

    @ExceptionHandler(InvalidConditionException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Object handleInvalidConditionException(InvalidConditionException e) {
        return new ErrorResult(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult handleAllException(Exception e) {
        return new ErrorResult(e);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorResult {
        private final String message;

        ErrorResult(Exception e) {
            this.message = e.getMessage();
        }
    }

}
