package com.starwacki.budgettracker.expense;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter(value = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
@Builder
@Table(name = "expenses")
class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String username;

    @Enumerated(value = EnumType.STRING)
    private ExpenseCategory expenseCategory;

    private LocalDate date;

    private LocalTime time;

    private double moneyValue;


    void updateObject(ExpenseDTO expenseDTO) {
        this.name = expenseDTO.name();
        this.date = expenseDTO.date();
        this.time = expenseDTO.time();
        this.description = expenseDTO.description();
        this.moneyValue = expenseDTO.moneyValue();
        this.expenseCategory = expenseDTO.expenseCategory();
    }

}
