package com.starwacki.budgettracker.chart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
class ChartFactory {

    private final BarChartCreatorStrategy barChartCreatorStrategy;

    private final PieChartCreatorStrategy pieChartCreatorStrategy;


    BarChart<?> getBarChart(BarChartType type, List<ChartExpense> expenses) {
        BarChart<?> chart;
        switch (type) {
            case DAILY -> chart = barChartCreatorStrategy.createDaysBarChart(expenses);
            case MONTHLY -> chart = barChartCreatorStrategy.createMonthsBarChart(expenses);
            case EXPENSE_CATEGORY -> chart = barChartCreatorStrategy.createExpensiveCategoryBarChart(expenses);
            default -> throw new IllegalArgumentException();
        }
        return chart;
    }

    PieChart getPieChart(List<ChartExpense> expenses) {
        return pieChartCreatorStrategy.createPieChart(expenses);
    }


    enum BarChartType {
        DAILY,
        MONTHLY,
        EXPENSE_CATEGORY,
    }

}
