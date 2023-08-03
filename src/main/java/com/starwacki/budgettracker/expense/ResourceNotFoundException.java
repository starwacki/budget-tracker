package com.starwacki.budgettracker.expense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class ResourceNotFoundException extends RuntimeException{
    private Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    <T> ResourceNotFoundException(Class<T> tclass,String message) {
        super(message);
        logger.error(tclass.getName() + " throw exception with message: " + message);
    }
}
