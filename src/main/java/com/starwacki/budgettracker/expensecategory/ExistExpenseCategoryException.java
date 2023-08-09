package com.starwacki.budgettracker.expensecategory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ExistExpenseCategoryException extends RuntimeException {

    ExistExpenseCategoryException(String categoryName) {
        super(categoryName);
        Logger logger = LoggerFactory.getLogger(ExistExpenseCategoryException.class);
        logger.error("Category with name: " + categoryName + " already exist!");
    }
}
