package com.starwacki.budgettracker.graph;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
class GraphCreatorStrategy {

    public GraphOfExpenses createGraph(List<GraphExpense> expenses) {
        HashMap<String, GraphCategory> graphData = new HashMap<>();
        getOnlyMoneyValuesFromExpenses(graphData,expenses);
        calculatePercentDistributionOfCategories(graphData);
        return new GraphOfExpenses(graphData);
    }

    private void calculatePercentDistributionOfCategories(HashMap<String, GraphCategory> graphData) {
        double allMoneyValue = getAllMoneyValue(graphData);
        calculatePercentDistributionForAllCategories(allMoneyValue,graphData);
    }

    private void calculatePercentDistributionForAllCategories(double allMoneyValue,HashMap<String, GraphCategory> graphData) {
        graphData.forEach((expenseCategory, graphCategory) ->
                graphCategory.setPercentAmount(getPercentValue(allMoneyValue, graphCategory.getMoneyAmount())
        ));
    }

    private double getPercentValue(double allMoneyValue, double expenseMoneyValue) {
        return expenseMoneyValue/allMoneyValue*100;
    }

    private double getAllMoneyValue(HashMap<String, GraphCategory> graphData) {
        return graphData
                .values()
                .stream()
                .map(GraphCategory::getMoneyAmount)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private void getOnlyMoneyValuesFromExpenses(HashMap<String, GraphCategory> graphData, List<GraphExpense> expenses) {
        expenses.forEach(expense -> addOnlyMoneyValuesToCategories(graphData,expense));
    }

    private void addOnlyMoneyValuesToCategories(HashMap<String, GraphCategory> graphData, GraphExpense expense) {
        if (!isCategoryExist(graphData,expense))
            putNewCategoryToGraph(graphData,expense);
        else
            addOnlyMoneyValueToCategory(graphData,expense);
    }

    private boolean isCategoryExist(HashMap<String, GraphCategory> graphData, GraphExpense expense) {
        return graphData.containsKey(expense.getExpenseCategory());
    }

    private void putNewCategoryToGraph(HashMap<String, GraphCategory> graphData, GraphExpense expense) {
        graphData.put(expense.getExpenseCategory(),new GraphCategory(expense.getMoneyValue(),0));
    }

    private void addOnlyMoneyValueToCategory(HashMap<String, GraphCategory> graphData, GraphExpense expense) {
        double actualMoneyValue = graphData.get(expense.getExpenseCategory()).getMoneyAmount();
        graphData.get(expense.getExpenseCategory())
                .setMoneyAmount(actualMoneyValue+expense.getMoneyValue());
    }


}
