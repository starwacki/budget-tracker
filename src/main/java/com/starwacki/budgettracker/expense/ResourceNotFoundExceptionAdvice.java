package com.starwacki.budgettracker.expense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class ResourceNotFoundExceptionAdvice extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundExceptionAdvice.class);

    @ExceptionHandler(value = {
            ResourceNotFoundException.class
    })
    public ResponseEntity<?> handleWebException(RuntimeException e, WebRequest webRequest) {

        String response = e.getMessage();

        return handleExceptionInternal(e, response, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, webRequest);
    }
}
