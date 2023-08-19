package com.shellwe.back.exception.handler;

import com.shellwe.back.exception.response.ControllerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ControllerErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("controller valid error", methodArgumentNotValidException);
        return ControllerErrorResponse.of(methodArgumentNotValidException.getBindingResult());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ControllerErrorResponse handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        log.error("controller valid error", constraintViolationException);
        return ControllerErrorResponse.of(constraintViolationException.getConstraintViolations());
    }
}