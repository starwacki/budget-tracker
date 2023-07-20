package com.starwacki.budgettracker.expense;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
class ExpenseGraphCreatorStrategy {

    public ExpenseGraph createGraph(List<Expense> expenses) {
        HashMap<ExpenseCategory, ExpenseGraphCategory> graphData = new HashMap<>();
        getOnlyMoneyValuesFromExpenses(graphData,expenses);
        calculatePercentDistributionOfCategories(graphData);
        return new ExpenseGraph(graphData);
    }

    private void calculatePercentDistributionOfCategories(HashMap<ExpenseCategory, ExpenseGraphCategory> graphData) {
        double allMoneyValue = getAllMoneyValue(graphData);
        calculatePercentDistributionForAllCategories(allMoneyValue,graphData);
    }

    private void calculatePercentDistributionForAllCategories(double allMoneyValue,HashMap<ExpenseCategory, ExpenseGraphCategory> graphData) {
        graphData.forEach((expenseCategory, expenseGraphCategory) ->
                expenseGraphCategory.setPercentAmount(getPercentValue(allMoneyValue,expenseGraphCategory.getMoneyAmount())
        ));
    }

    private double getPercentValue(double allMoneyValue, double expenseMoneyValue) {
        return expenseMoneyValue/allMoneyValue*100;
    }

    private double getAllMoneyValue(HashMap<ExpenseCategory, ExpenseGraphCategory> graphData) {
        return graphData
                .values()
                .stream()
                .map(ExpenseGraphCategory::getMoneyAmount)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private void getOnlyMoneyValuesFromExpenses(HashMap<ExpenseCategory, ExpenseGraphCategory> graphData,List<Expense> expenses) {
        expenses.forEach(expense -> addOnlyMoneyValuesToCategories(graphData,expense));
    }

    private void addOnlyMoneyValuesToCategories(HashMap<ExpenseCategory, ExpenseGraphCategory> graphData, Expense expense) {
        if (isCategoryExist(graphData,expense))
            putNewCategoryToGraph(graphData,expense);
        else
            addOnlyMoneyValueToCategory(graphData,expense);
    }

    private boolean isCategoryExist(HashMap<ExpenseCategory, ExpenseGraphCategory> graphData, Expense expense) {
        return graphData.containsKey(expense.getExpenseCategory());
    }

    private void putNewCategoryToGraph(HashMap<ExpenseCategory, ExpenseGraphCategory> graphData, Expense expense) {
        graphData.put(expense.getExpenseCategory(),new ExpenseGraphCategory(expense.getMoneyValue(),0));
    }

    private void addOnlyMoneyValueToCategory(HashMap<ExpenseCategory, ExpenseGraphCategory> graphData, Expense expense) {
        double actualMoneyValue = graphData.get(expense.getExpenseCategory()).getMoneyAmount();
        graphData.get(expense.getExpenseCategory())
                .setMoneyAmount(actualMoneyValue+expense.getMoneyValue());
    }


}
