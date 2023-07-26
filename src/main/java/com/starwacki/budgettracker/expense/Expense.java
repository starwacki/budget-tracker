package com.starwacki.budgettracker.expense;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

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

}
