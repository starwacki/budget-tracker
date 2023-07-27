package com.starwacki.budgettracker.graph;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class GraphService {

    private final GraphExpenseQueryRepository graphExpenseQueryRepository;
    private final GraphCreatorStrategy graphCreatorStrategy;
    GraphOfExpenses createGraphOfUserExpenses(String username) {
        return graphCreatorStrategy.createGraph(graphExpenseQueryRepository.findAllUsernameGraphExpenses(username));
    }

    GraphOfExpenses createGraphOfUserExpensesWhenExpenseCategory(String username, String expenseCategory) {
        return graphCreatorStrategy.createGraph(graphExpenseQueryRepository.findAllUsernameGraphExpensesWithThisExpenseCategory(username,expenseCategory));
    }

    GraphOfExpenses createGraphOfUserExpensesWhenDate(String username, LocalDate date) {
        return graphCreatorStrategy.createGraph(graphExpenseQueryRepository.findAllDayGraphExpenses(username,date));
    }

    GraphOfExpenses createGraphOfUserExpensesWhenMonth(String username, int monthOrder) {
        return graphCreatorStrategy.createGraph(graphExpenseQueryRepository.findAllMonthGraphExpenses(username,monthOrder));
    }

    GraphOfExpenses createGraphOfUserExpensesWhenYear(String username, int year) {
        return graphCreatorStrategy.createGraph(graphExpenseQueryRepository.findAllYearGraphExpenses(username,year));
    }

    GraphOfExpenses createGraphOfUserExpensesWhenPeriod(String username, LocalDate startDate, LocalDate endDate) {
        return graphCreatorStrategy.createGraph(graphExpenseQueryRepository.findAllPeriodGraphExpenses(username,startDate,endDate));
    }
}
