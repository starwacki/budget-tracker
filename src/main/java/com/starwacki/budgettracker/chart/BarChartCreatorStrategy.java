package com.starwacki.budgettracker.chart;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;

@Component
class BarChartCreatorStrategy extends ChartCreatorStrategy  {

    BarChart<DayOfWeek> createDaysBarChart(List<ChartExpense> expenses) {
        return new BarChart<>(createChart(expenses,chartExpense -> chartExpense.getDate().getDayOfWeek()));
    }

    BarChart<Month> createMonthsBarChart(List<ChartExpense> expenses) {
       return new BarChart<>(createChart(expenses,chartExpense -> chartExpense.getDate().getMonth()));
    }

    BarChart<String> createExpensiveCategoryBarChart(List<ChartExpense> expenses) {
        return new BarChart<>(createChart(expenses,chartExpense -> chartExpense.getExpenseCategory()));
    }


}