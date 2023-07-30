package com.starwacki.budgettracker.chart;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

abstract class ChartCreatorStrategy {


    /**
     * Function create chart by given expenses and bar chart (operation).
     * The most important thing is to correct define operation and give filtered list of expenses.
     * For example: If you will to get weekly expenses chart, and you give T (segment) as DayOfWeek
     * ChartCategory will be creating only by DayOfWeek (without date care).
     * @param expenses List of expenses - should be filtered before calling the method.
     * @param chartExpenseOperation operation that return our chart segment type.
     * @return HashMap<T,ChartCategory> - our chart.
     * @param <T> is one of chart segments. For example: if you create weekly expenses chart
     * You have 7 segments: Monday, Tuesday .... Sunday
     * created by Szymon Tarwacki 30.07.2023
     */
    <T> HashMap<T, ChartCategory> createChart(List<ChartExpense> expenses, ChartExpenseOperation<T> chartExpenseOperation) {
        HashMap<T, ChartCategory> chart = new HashMap<>();
        expenses.forEach(chartExpense -> fillChart(chartExpenseOperation.doOperation(chartExpense), chart, chartExpense));
        calculatePercentDistribution(chart);
        return chart;
    }


    /**
     * Operation to separation chart segments
     */
    @FunctionalInterface
    interface ChartExpenseOperation<T> {
        T doOperation(ChartExpense chartExpense);

    }

    /**
     * @param bar - chart segment.
     * Operation extract expenses related to a specific segment and add them to the category.
     */
    private <T> void fillChart(T bar, HashMap<T, ChartCategory> chart, ChartExpense chartExpense) {
        checkMapContainKey(bar, chart);
        addMoneyToChartCategory(chart, bar, chartExpense);
    }

    private <T> void calculatePercentDistribution(HashMap<T, ChartCategory> chart) {
        double sumOfAllSpendMoney = getSumOfSpentMoney(chart);
        calculatePercentForEachGraphCategory(sumOfAllSpendMoney, chart);
    }

    private <T> void checkMapContainKey(T bar, HashMap<T, ChartCategory> chart) {
        if (!chart.containsKey(bar))
            chart.put(bar, new ChartCategory(0, ""));
    }

    private <T> void addMoneyToChartCategory(HashMap<T, ChartCategory> chart, T bar, ChartExpense chartExpense) {
        chart.get(bar).setMoneyAmount(chart.get(bar).getMoneyAmount() + chartExpense.getMoneyValue());
    }

    private <T> double getSumOfSpentMoney(HashMap<T, ChartCategory> chart) {
        return chart.values()
                .stream()
                .map(ChartCategory::getMoneyAmount)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private <T> void calculatePercentForEachGraphCategory(double sumOfSpentMoney, HashMap<T, ChartCategory> chart) {
        chart.forEach((t, chartCategory) -> chartCategory.setPercentageAmount(calculatePercentAmount(sumOfSpentMoney, chartCategory.getMoneyAmount())));
    }

    private String calculatePercentAmount(double sumOfSpentMoney, double actualMoneyAmount) {
        double percent = actualMoneyAmount / sumOfSpentMoney * 100;
        return new DecimalFormat("#.##").format(percent) + "%";
    }


}
