package com.starwacki.budgettracker.expense;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@AllArgsConstructor
@Getter
@Setter
@ToString
class ExpenseGraph {

    private HashMap<ExpenseCategory, ExpenseGraphCategory> expenses;

}
