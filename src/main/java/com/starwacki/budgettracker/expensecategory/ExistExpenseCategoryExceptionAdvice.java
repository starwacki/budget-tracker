package com.starwacki.budgettracker.expensecategory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class ExistExpenseCategoryExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ExistExpenseCategoryException.class)
    public ResponseEntity<?> handleWebException(RuntimeException e, WebRequest webRequest) {

        String response = e.getMessage();

        return handleExceptionInternal(e, response, HttpHeaders.EMPTY, HttpStatus.CONFLICT, webRequest);
    }
}
