package com.starwacki.budgettracker.chart;

import org.springframework.stereotype.Component;

@Component
final class ChartMapper {

    private ChartMapper() {}

    <T> ChartDTO<T> mapChartToChartDTO(Chart<T> chart) {
        return new ChartDTO<>(chart.getExpenses());
    }

}
