package com.starwacki.budgettracker.chart;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

abstract class ChartCreatorStrategy {

    <T> HashMap<T, ChartCategory> createChart(List<ChartExpense> expenses, ChartExpenseOperation<T> chartExpenseOperation) {
        HashMap<T, ChartCategory> chart = new HashMap<>();
        expenses.forEach(chartExpense -> fillChart(chartExpenseOperation.doOperation(chartExpense), chart, chartExpense));
        calculatePercentDistribution(chart);
        return chart;
    }

    @FunctionalInterface
    interface ChartExpenseOperation<T> {
        T doOperation(ChartExpense chartExpense);

    }

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