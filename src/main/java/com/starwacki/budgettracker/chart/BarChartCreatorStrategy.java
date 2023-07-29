package com.starwacki.budgettracker.chart;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

class BarChartCreatorStrategy  {

    public static BarChart<DayOfWeek> createWeekBarChart(List<ChartExpense> expenses) {
        HashMap<DayOfWeek,ChartCategory> chart = new HashMap<>();
        expenses.forEach(chartExpense -> fillChart(chartExpense.getDate().getDayOfWeek(),chart,chartExpense));
        return new BarChart<>(chart);
    }

    public static BarChart<Month> createOneYearBarChart(List<ChartExpense> expenses) {
        HashMap<Month,ChartCategory> chart = new HashMap<>();
        expenses.forEach(chartExpense -> fillChart(chartExpense.getDate().getMonth(),chart,chartExpense));
        return new BarChart<>(chart);
    }

    private static <T> void fillChart(T bar, HashMap<T,ChartCategory> chart, ChartExpense chartExpense) {
        checkMapContainKey(bar,chart);
        addMoneyToChartCategory(chart,bar,chartExpense);
    }

    private static <T> void checkMapContainKey(T bar, HashMap<T,ChartCategory> chart)  {
        if (!chart.containsKey(bar))
            chart.put(bar,new ChartCategory(0,0));
    }

    private static  <T> void addMoneyToChartCategory(HashMap<T,ChartCategory> chart, T bar, ChartExpense chartExpense) {
        chart.get(bar).setMoneyAmount(chart.get(bar).getMoneyAmount()+chartExpense.getMoneyValue());
    }




}
