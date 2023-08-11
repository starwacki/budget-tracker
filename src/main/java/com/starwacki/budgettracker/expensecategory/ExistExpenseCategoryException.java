package com.starwacki.budgettracker.expensecategory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
class ExistExpenseCategoryException extends RuntimeException {

    ExistExpenseCategoryException(String categoryName) {
        super(categoryName);
    }
}
