package com.starwacki.budgettracker.chart;

import java.util.HashMap;

/**
 *
 * @param <T> is a one of chart categories (bar)
 */
class BarChart<T> extends Chart<T>{
    public BarChart(HashMap<T,ChartCategory> expenses) {
        super(expenses);
    }
}
