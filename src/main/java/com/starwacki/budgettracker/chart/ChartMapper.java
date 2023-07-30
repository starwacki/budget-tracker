package com.starwacki.budgettracker.chart;

final class ChartMapper {

    <T> ChartDTO<T> mapChartToChartDTO(Chart<T> chart) {
        return new ChartDTO<>(chart.getExpenses());
    }

}
