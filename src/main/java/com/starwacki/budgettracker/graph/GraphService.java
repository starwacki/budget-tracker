package com.starwacki.budgettracker.graph;

import com.starwacki.budgettracker.expense.ExpenseCategory;
import com.starwacki.budgettracker.expense.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class ExpenseGraphService {

    private final ExpenseRepository expenseRepository;
    private final GraphCreatorStrategy graphCreatorStrategy;
    GraphOfExpenses getAllExpensesGraphByUsername(String username) {
        return graphCreatorStrategy.createGraph(expenseRepository.findAllUsernameExpenses(username));
    }

    GraphOfExpenses getAllExpensesGraphByUsernameAndExpenseCategory(String username, ExpenseCategory expenseCategory) {
        return graphCreatorStrategy.createGraph(expenseRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory));
    }

    GraphOfExpenses getAllExpensesGraphByUsernameAndDate(String username, LocalDate date) {
        return graphCreatorStrategy.createGraph(expenseRepository.findAllDayExpenses(username,date));
    }

    GraphOfExpenses getAllExpensesGraphByUsernameAndMonth(String username, int monthOrder) {
        return graphCreatorStrategy.createGraph(expenseRepository.findAllMonthExpenses(username,monthOrder));
    }
}
