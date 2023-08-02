package com.starwacki.budgettracker.chart;

import lombok.Getter;

import java.util.HashMap;
import java.util.Objects;

@Getter
abstract class Chart<T> {

    private HashMap<T, ChartCategory> expenses;

    Chart(HashMap<T, ChartCategory> expenses) {
        this.expenses = expenses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chart<?> chart = (Chart<?>) o;
        return Objects.equals(expenses, chart.expenses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenses);
    }

}
