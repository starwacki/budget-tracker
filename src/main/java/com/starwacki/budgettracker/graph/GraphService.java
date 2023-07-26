package com.starwacki.budgettracker.graph;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class GraphService {

    private final GraphExpenseQueryRepository graphExpenseQueryRepository;
    private final GraphCreatorStrategy graphCreatorStrategy;
    GraphOfExpenses getAllExpensesGraphByUsername(String username) {
        return graphCreatorStrategy.createGraph(graphExpenseQueryRepository.findAllUsernameExpenses(username));
    }

    GraphOfExpenses getAllExpensesGraphByUsernameAndExpenseCategory(String username, String expenseCategory) {
        return graphCreatorStrategy.createGraph(graphExpenseQueryRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory));
    }

    GraphOfExpenses getAllExpensesGraphByUsernameAndDate(String username, LocalDate date) {
        return graphCreatorStrategy.createGraph(graphExpenseQueryRepository.findAllDayExpenses(username,date));
    }

    GraphOfExpenses getAllExpensesGraphByUsernameAndMonth(String username, int monthOrder) {
        return graphCreatorStrategy.createGraph(graphExpenseQueryRepository.findAllMonthExpenses(username,monthOrder));
    }
}
