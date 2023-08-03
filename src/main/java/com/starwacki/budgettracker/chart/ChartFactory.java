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
        return switch (type) {
            case DAILY -> barChartCreatorStrategy.createDaysBarChart(expenses);
            case MONTHLY -> barChartCreatorStrategy.createMonthsBarChart(expenses);
        };
    }

    PieChart getPieChart(List<ChartExpense> expenses) {
        return pieChartCreatorStrategy.createPieChart(expenses);
    }


    enum BarChartType {
        DAILY,
        MONTHLY,
    }

}
