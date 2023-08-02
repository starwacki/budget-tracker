package com.starwacki.budgettracker.chart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PieChartCreatorStrategyUnitTest {

    private PieChartCreatorStrategy pieChartCreatorStrategy;

    @BeforeEach
    void setUp() {
        pieChartCreatorStrategy = new PieChartCreatorStrategy();
    }

    @Test
    @DisplayName("Test createPieChart()  return empty hashmap when given empty list")
    void should_ReturnEmptyExpensiveCategoryChart() {

        //given
        List<ChartExpense> emptyList = new ArrayList<>();

        //then
        PieChart chart = pieChartCreatorStrategy.createPieChart(emptyList);
        assertThat(chart.getExpenses().size(),is(0));
    }

    @Test
    @DisplayName("Test createPieChart() return hashmap with 3 elements when given list of 3 days expenses")
    void should_ReturnWeeklyChart() {

        //given
        List<ChartExpense> expenses = List.of(
                ChartExpense.builder().expenseCategory("FOOD").build(),
                ChartExpense.builder().expenseCategory("OTHER").build(),
                ChartExpense.builder().expenseCategory("CAR").build()
        );

        //then
        PieChart chart = pieChartCreatorStrategy.createPieChart(expenses);
        assertThat(chart.getExpenses().size(),is(3));

    }

    @RepeatedTest(50)
    @DisplayName("Test correct percent distribution")
    void should_ReturnOneHundredPercentSumOfEachCategory() {

        //given
        double maxValue = 100;
        double minValue = 10;
        //given
        List<ChartExpense> expenses = List.of(
                ChartExpense.builder().expenseCategory("FOOD").moneyValue(ThreadLocalRandom.current().nextDouble(minValue,maxValue)).build(),
                ChartExpense.builder().expenseCategory("FOOD").moneyValue(ThreadLocalRandom.current().nextDouble(minValue,maxValue)).build(),
                ChartExpense.builder().expenseCategory("OTHER").moneyValue(ThreadLocalRandom.current().nextDouble(minValue,maxValue)).build(),
                ChartExpense.builder().expenseCategory("OTHER").moneyValue(ThreadLocalRandom.current().nextDouble(minValue,maxValue)).build(),
                ChartExpense.builder().expenseCategory("CAR").moneyValue(ThreadLocalRandom.current().nextDouble(minValue,maxValue)).build(),
                ChartExpense.builder().expenseCategory("CAR").moneyValue(ThreadLocalRandom.current().nextDouble(minValue,maxValue)).build()
        );


        //when
        PieChart chart = pieChartCreatorStrategy.createPieChart(expenses);
        double sumOfPercentDistribution = chart.getExpenses().values()
                .stream()
                .map(chartCategory -> Double.valueOf(chartCategory.getPercentageAmount().replace(",",".").replace("%","")))
                .mapToDouble(Double::doubleValue)
                .sum();

        assertEquals(100, Double.valueOf(new DecimalFormat("#.###").format(sumOfPercentDistribution)));
    }

    @Test
    @DisplayName("Test createPieChart() return chart with correct money amount")
    void should_ReturnCorrectMoneyAmount() {

        //given
        List<ChartExpense> expenses = List.of(
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).expenseCategory("FOOD").moneyValue(50).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).expenseCategory("FOOD").moneyValue(60).build(),
                ChartExpense.builder().date(LocalDate.of(2023,9,2)).expenseCategory("OTHER").moneyValue(30).build(),
                ChartExpense.builder().date(LocalDate.of(2023,9,2)).expenseCategory("OTHER").moneyValue(40).build(),
                ChartExpense.builder().date(LocalDate.of(2023,10,3)).expenseCategory("CAR").moneyValue(10.5).build()
        );


        //then
        PieChart chart = pieChartCreatorStrategy.createPieChart(expenses);
        assertThat(chart.getExpenses().size(),is(3));
        assertThat(chart.getExpenses().get("FOOD").getMoneyAmount(),is(110.0));
        assertThat(chart.getExpenses().get("OTHER").getMoneyAmount(),is(70.0));
        assertThat(chart.getExpenses().get("CAR").getMoneyAmount(),is(10.5));
    }




}
