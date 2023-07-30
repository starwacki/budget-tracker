package com.starwacki.budgettracker.chart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class ChartService {

    private final ChartExpenseQueryRepository chartExpenseQueryRepository;
    private final ChartFactory chartFactory;
    Chart<?> createGraphOfUserExpenses(String username) {
        return graphCreatorStrategy.createGraph(chartExpenseQueryRepository.findAllUsernameChartExpenses(username));
    }

    Chart<?> createGraphOfUserExpensesWhenExpenseCategory(String username, String expenseCategory) {
        return graphCreatorStrategy.createGraph(chartExpenseQueryRepository.findAllUsernameChartExpensesWithThisExpenseCategory(username,expenseCategory));
    }

    Chart<?> createGraphOfUserExpensesWhenDate(String username, LocalDate date) {
        return graphCreatorStrategy.createGraph(chartExpenseQueryRepository.findAllDayChartExpenses(username,date));
    }

    Chart<?> createGraphOfUserExpensesWhenMonth(String username, int monthOrder) {
        return graphCreatorStrategy.createGraph(chartExpenseQueryRepository.findAllMonthChartExpenses(username,monthOrder));
    }

    Chart<?> createGraphOfUserExpensesWhenYear(String username, int year) {
        return graphCreatorStrategy.createGraph(chartExpenseQueryRepository.findAllYearChartExpenses(username,year));
    }

    Chart<?> createGraphOfUserExpensesWhenPeriod(String username, LocalDate startDate, LocalDate endDate) {
        return graphCreatorStrategy.createGraph(chartExpenseQueryRepository.findAllPeriodChartExpenses(username,startDate,endDate));
    }
}
