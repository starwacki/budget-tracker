package com.starwacki.budgettracker.expense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException{

    <T> ResourceNotFoundException(Class<T> tclass,String message) {
        super(message);
        Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);
        logger.error(tclass.getName() + " throw exception with message: " + message);
    }
}
