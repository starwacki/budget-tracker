package com.starwacki.budgettracker.expense;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
record ExpenseDTO(

        String name,
        String description,
        String username,
        ExpenseCategory expenseCategory,
        LocalDate date,
        LocalTime time,
        double moneyValue

) {
}