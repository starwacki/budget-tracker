package com.starwacki.budgettracker.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class ExpenseGraphService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseGraphCreatorStrategy expenseGraphCreatorStrategy;
    ExpenseGraph getAllExpensesGraphByUsername(String username) {
        return expenseGraphCreatorStrategy.createGraph(expenseRepository.findAllByUsername(username));
    }

    ExpenseGraph getAllExpensesGraphByUsernameAndExpenseCategory(String username, ExpenseCategory expenseCategory) {
        return expenseGraphCreatorStrategy.createGraph(expenseRepository.findAllByUsernameAndExpenseCategory(username,expenseCategory));
    }

    ExpenseGraph getAllExpensesGraphByUsernameAndDate(String username, LocalDate date) {
        return expenseGraphCreatorStrategy.createGraph(expenseRepository.findAllByUsernameAndDate(username,date));
    }

    ExpenseGraph getAllExpensesGraphByUsernameAndMonth(String username, int monthOrder) {
        return expenseGraphCreatorStrategy.createGraph(expenseRepository.findAllByUsernameAndMonth(username,monthOrder));
    }
}
