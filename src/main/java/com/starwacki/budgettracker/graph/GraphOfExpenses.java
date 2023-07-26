package com.starwacki.budgettracker.graph;

import com.starwacki.budgettracker.expense.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@AllArgsConstructor
@Getter
@Setter
@ToString
class GraphOfExpenses {

    private HashMap<ExpenseCategory, ExpenseGraphCategory> expenses;

}
