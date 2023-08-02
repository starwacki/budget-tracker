package com.starwacki.budgettracker.chart;

import java.util.HashMap;

/**
 *
 * @param <T> is a one of chart categories (bar)
 */

class BarChart<T> extends Chart<T>{

    BarChart(HashMap<T,ChartCategory> expenses) {
        super(expenses);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
