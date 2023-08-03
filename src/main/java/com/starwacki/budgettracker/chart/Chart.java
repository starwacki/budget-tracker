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


}
