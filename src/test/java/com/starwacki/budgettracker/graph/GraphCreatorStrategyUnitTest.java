package com.starwacki.budgettracker.graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class GraphCreatorStrategyUnitTest {

    @InjectMocks
    private GraphCreatorStrategy graphCreatorStrategy;

    @Test
    void Should_CreateExpenseGraph_When_ListIsEmpty() {

        //given
        List<GraphExpense> expenses = new ArrayList<>();

        //then
        GraphOfExpenses expenseGraph = graphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().size(),0);
        assertNotEquals(expenseGraph.getExpenses(),null);
    }

    @Test
    void Should_Return2ExpenseGraphCategories_WhenMultipleExpenseCategories() {

        //given
        List<GraphExpense> expenses = new ArrayList<>();
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(31.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("CAR").moneyValue(32.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("CAR").moneyValue(33.0).build());

        //then
        GraphOfExpenses expenseGraph = graphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().size(),2);
    }

    @Test
    void  Should_ReturnCorrectMoneyAmount_When_MultipleExpenseCategories() {

        //given
        List<GraphExpense> expenses = new ArrayList<>();
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(32.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(33.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("CAR").moneyValue(45.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("CAR").moneyValue(22.0).build());

        //when
        double moneyOfFoodCategory=32.0+33.0;
        double moneyOfCarCategory=45.0+22.0;

        //then
        GraphOfExpenses expenseGraph = graphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().get("FOOD").getMoneyAmount(),moneyOfFoodCategory);
        assertEquals(expenseGraph.getExpenses().get("CAR").getMoneyAmount(),moneyOfCarCategory);
    }

    @Test
    void Should_CreateGraphWithOneExpenseCategory() {

        //given
        List<GraphExpense> expenses = new ArrayList<>();
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(31.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(32.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(33.0).build());

        //then
        GraphOfExpenses expenseGraph = graphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().size(),1);
    }

    @Test
    void Should_ReturnCorrectMoneyAmount_When_OneExpenseCategory() {

        //given
        List<GraphExpense> expenses = new ArrayList<>();
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(31.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(32.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(33.0).build());

        //when
        double sumOfMoney = 31.0+32.0+33.0;

        //then
        GraphOfExpenses expenseGraph = graphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().get("FOOD").getMoneyAmount(),sumOfMoney);
    }

    @Test
    void Should_Return100PercentAmount_When_OneExpenseCategory() {

        //given
        List<GraphExpense> expenses = new ArrayList<>();
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(31.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(32.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(33.0).build());

        //then
        GraphOfExpenses expenseGraph = graphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().get("FOOD").getPercentAmount(),100);
    }

    @Test
    void Should_ReturnCorrectExpenseDistribution_When_MultipleExpenseCategories() {

        //given
        List<GraphExpense> expenses = new ArrayList<>();
        expenses.add(GraphExpense.builder().expenseCategory("FOOD").moneyValue(30.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("CAR").moneyValue(50.0).build());
        expenses.add(GraphExpense.builder().expenseCategory("DEBTS").moneyValue(20.0).build());

        //when
        double expectedFoodPercent = 30;
        double expectedCarPercent = 50;
        double expectedDebtsPercent = 20;

        //then
        GraphOfExpenses expenseGraph = graphCreatorStrategy.createGraph(expenses);
        assertEquals(expenseGraph.getExpenses().get("FOOD").getPercentAmount(),expectedFoodPercent);
        assertEquals(expenseGraph.getExpenses().get("CAR").getPercentAmount(),expectedCarPercent);
        assertEquals(expenseGraph.getExpenses().get("DEBTS").getPercentAmount(),expectedDebtsPercent);
    }

}