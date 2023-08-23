package com.starwacki.budgettracker.chart;

/**
 * Operation to separation chart segments.
 */
@FunctionalInterface
interface ChartExpenseOperation<T>{

    T doOperation(ChartExpense chartExpense);

}
