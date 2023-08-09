package com.starwacki.budgettracker.expense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class ResourceNotFoundException extends RuntimeException{

    <T> ResourceNotFoundException(Class<T> tclass,String message) {
        super(message);
        Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);
        logger.error(tclass.getName() + " throw exception with message: " + message);
    }
}
