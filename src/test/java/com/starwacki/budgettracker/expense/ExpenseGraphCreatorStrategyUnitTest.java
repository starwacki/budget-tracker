package com.starwacki.budgettracker.expense;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class ExpenseGraphCreatorStrategyUnitTest {

    @InjectMocks
    private ExpenseGraphCreatorStrategy expenseGraphCreatorStrategy;

    @Test
    void Should_CreateExpenseGraph_When_ListIsEmpty() {

        //given
        List<Expense> expenses = new ArrayList<>();

        //then
        ExpenseGraph expenseGraph = expenseGraphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().size(),0);
        assertNotEquals(expenseGraph.getExpenses(),null);
    }

    @Test
    void Should_Return2ExpenseGraphCategories_WhenMultipleExpenseCategories() {

        //given
        List<Expense> expenses = new ArrayList<>();
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(31.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.CAR).moneyValue(32.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.CAR).moneyValue(33.0).build());

        //then
        ExpenseGraph expenseGraph = expenseGraphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().size(),2);
    }

    @Test
    void  Should_ReturnCorrectMoneyAmount_When_MultipleExpenseCategories() {

        //given
        List<Expense> expenses = new ArrayList<>();
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(32.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(33.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.CAR).moneyValue(45.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.CAR).moneyValue(22.0).build());

        //when
        double moneyOfFoodCategory=32.0+33.0;
        double moneyOfCarCategory=45.0+22.0;

        //then
        ExpenseGraph expenseGraph = expenseGraphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().get(ExpenseCategory.FOOD).getMoneyAmount(),moneyOfFoodCategory);
        assertEquals(expenseGraph.getExpenses().get(ExpenseCategory.CAR).getMoneyAmount(),moneyOfCarCategory);
    }

    @Test
    void Should_CreateGraphWithOneExpenseCategory() {

        //given
        List<Expense> expenses = new ArrayList<>();
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(31.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(32.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(33.0).build());

        //then
        ExpenseGraph expenseGraph = expenseGraphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().size(),1);
    }

    @Test
    void Should_ReturnCorrectMoneyAmount_When_OneExpenseCategory() {

        //given
        List<Expense> expenses = new ArrayList<>();
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(31.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(32.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(33.0).build());

        //when
        double sumOfMoney = 31.0+32.0+33.0;

        //then
        ExpenseGraph expenseGraph = expenseGraphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().get(ExpenseCategory.FOOD).getMoneyAmount(),sumOfMoney);
    }

    @Test
    void Should_Return100PercentAmount_When_OneExpenseCategory() {

        //given
        List<Expense> expenses = new ArrayList<>();
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(31.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(32.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(33.0).build());

        //then
        ExpenseGraph expenseGraph = expenseGraphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().get(ExpenseCategory.FOOD).getPercentAmount(),100);
    }

    @Test
    void Should_ReturnCorrectExpenseDistribution_When_MultipleExpenseCategories() {

        //given
        List<Expense> expenses = new ArrayList<>();
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.FOOD).moneyValue(30.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.CAR).moneyValue(50.0).build());
        expenses.add(Expense.builder().expenseCategory(ExpenseCategory.DEBTS).moneyValue(20.0).build());

        //when
        double expectedFoodPercent = 30;
        double expectedCarPercent = 50;
        double expectedDebtsPercent = 20;

        //then
        ExpenseGraph expenseGraph = expenseGraphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().get(ExpenseCategory.FOOD).getPercentAmount(),expectedFoodPercent);
        assertEquals(expenseGraph.getExpenses().get(ExpenseCategory.CAR).getPercentAmount(),expectedCarPercent);
        assertEquals(expenseGraph.getExpenses().get(ExpenseCategory.DEBTS).getPercentAmount(),expectedDebtsPercent);
    }

}
