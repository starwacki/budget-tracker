package com.starwacki.budgettracker.expense;

import jakarta.validation.constraints.*;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
record ExpenseDTO(
        @NotBlank(message = "The Name of expense shouldn't be empty!")
        @Length(min = 3,max = 40, message = "Name of expense should have between 3 and 40 characters")
        String name,
        @Length(max = 200,message = "The Description should less than 200 characters")
        String description,
        ExpenseCategory expenseCategory,
        LocalDate date,
        LocalTime time,
        @Range(min = 0,max = 1000000, message = "Money value should have values between 0 and 1000000")
        double moneyValue

) {
}