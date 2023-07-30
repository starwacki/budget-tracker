package com.starwacki.budgettracker.chart;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PieChartCreatorStrategy extends ChartCreatorStrategy {

    PieChart createPieChart(List<ChartExpense> expenses) {
        return new PieChart(createChart(expenses,ChartExpense::getExpenseCategory));
    }

}
