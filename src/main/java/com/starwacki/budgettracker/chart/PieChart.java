package com.starwacki.budgettracker.chart;

import java.util.HashMap;

/**
 *
 * PieChart is always created when T param of Chart<T> is String
 * because String in HashMap<String, ChartCategory> expenses is
 * one of user expenses category.
 */
class PieChart extends Chart<String>{

    public PieChart(HashMap<String, ChartCategory> expenses) {
        super(expenses);
    }

}
