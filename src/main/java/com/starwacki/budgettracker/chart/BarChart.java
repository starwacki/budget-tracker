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
}
