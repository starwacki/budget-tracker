package com.starwacki.budgettracker.chart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BarChartCreatorStrategyUnitTest {

    private BarChartCreatorStrategy barChartCreatorStrategy;

    @BeforeEach
    void setUp() {
        barChartCreatorStrategy = new BarChartCreatorStrategy();
    }

    @Test
    @DisplayName("Test createDaysBarChart() return empty hashmap when given empty list")
    void should_ReturnEmptyWeeklyChart() {

        //given
        List<ChartExpense> emptyList = new ArrayList<>();

        //then
        BarChart<DayOfWeek> chart = barChartCreatorStrategy.createDaysBarChart(emptyList);
        assertThat(chart.getExpenses().size(),is(0));
    }

    @Test
    @DisplayName("Test createMonthsBarChart() return empty hashmap when given empty list")
    void should_ReturnEmptyYearlyChart() {

        //given
        List<ChartExpense> emptyList = new ArrayList<>();

        //then
        BarChart<Month> chart = barChartCreatorStrategy.createMonthsBarChart(emptyList);
        assertThat(chart.getExpenses().size(),is(0));
    }

    @Test
    @DisplayName("Test createYearsBarChart() return empty hashmap when given empty list")
    void should_ReturnEmptyExpensiveCategoryChart() {

        //given
        List<ChartExpense> emptyList = new ArrayList<>();

        //then
        BarChart<String> chart = barChartCreatorStrategy.createExpensiveCategoryBarChart(emptyList);
        assertThat(chart.getExpenses().size(),is(0));
    }

    @Test
    @DisplayName("Test createDaysBarChart() return hashmap with 3 elements when given list of 3 days expenses")
    void should_ReturnWeeklyChart() {

        //given
        List<ChartExpense> oneWeekExpenses = List.of(
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,2)).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,3)).build()
        );

        //then
        BarChart<DayOfWeek> actualChart = barChartCreatorStrategy.createDaysBarChart(oneWeekExpenses);
        assertThat(actualChart.getExpenses().size(),is(3));

    }

    @Test
    @DisplayName("Test createMonthsBarChart() return hashmap with 3 elements when given list of 3 months expenses")
    void should_ReturnMonthlyChart() {

        //given
        List<ChartExpense> oneWeekExpenses = List.of(
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).build(),
                ChartExpense.builder().date(LocalDate.of(2023,9,2)).build(),
                ChartExpense.builder().date(LocalDate.of(2023,10,3)).build()
        );

        //then
        BarChart<Month> actualChart = barChartCreatorStrategy.createMonthsBarChart(oneWeekExpenses);
        assertThat(actualChart.getExpenses().size(),is(3));
    }

    @Test
    @DisplayName("Test createExpensiveCategoryBarChart() return hashmap with 3 elements when given list of 3 categories expenses")
    void should_ReturnExpensiveCategoryChart() {

        //given
        List<ChartExpense> oneWeekExpenses = List.of(
                ChartExpense.builder().expenseCategory("FOOD").build(),
                ChartExpense.builder().expenseCategory("OTHER").build(),
                ChartExpense.builder().expenseCategory("CAR").build()
        );

        //then
        BarChart<String> actualChart = barChartCreatorStrategy.createExpensiveCategoryBarChart(oneWeekExpenses);
        assertThat(actualChart.getExpenses().size(),is(3));
    }

    @RepeatedTest(100)
    @DisplayName("Test correct percent distribution")
    void should_ReturnOneHundredPercentSumOfEachCategory() {

        //given
        double maxValue = 100;
        double minValue = 10;
        List<ChartExpense> oneWeekExpenses = List.of(
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).moneyValue(ThreadLocalRandom.current().nextDouble(minValue,maxValue)).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).moneyValue(ThreadLocalRandom.current().nextDouble(minValue,maxValue)).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,2)).moneyValue(ThreadLocalRandom.current().nextDouble(minValue,maxValue)).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,2)).moneyValue(ThreadLocalRandom.current().nextDouble(minValue,maxValue)).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,3)).moneyValue(ThreadLocalRandom.current().nextDouble(minValue,maxValue)).build()
        );

        //when
        BarChart<DayOfWeek> chart = barChartCreatorStrategy.createDaysBarChart(oneWeekExpenses);
        double sumOfPercentDistribution = chart.getExpenses().values()
                .stream()
                .map(chartCategory -> Double.valueOf(chartCategory.getPercentageAmount().replace(",",".").replace("%","")))
                .mapToDouble(Double::doubleValue)
                .sum();

        assertEquals(100, Double.valueOf(new DecimalFormat("#.###").format(sumOfPercentDistribution)));

    }

    @Test
    @DisplayName("Test createDaysBarChart() return chart with correct money amount")
    void should_ReturnCorrectMoneyAmount_daysBarChart() {

        //given
        List<ChartExpense> oneWeekExpenses = List.of(
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).moneyValue(50).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).moneyValue(60).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,2)).moneyValue(30).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,2)).moneyValue(40).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,3)).moneyValue(10.5).build()
        );

        //then
        BarChart<DayOfWeek> actualChart = barChartCreatorStrategy.createDaysBarChart(oneWeekExpenses);
        assertThat(actualChart.getExpenses().size(),is(3));
        assertThat(actualChart.getExpenses().get(LocalDate.of(2023,8,1).getDayOfWeek()).getMoneyAmount(),is(110.0));
        assertThat(actualChart.getExpenses().get(LocalDate.of(2023,8,2).getDayOfWeek()).getMoneyAmount(),is(70.0));
        assertThat(actualChart.getExpenses().get(LocalDate.of(2023,8,3).getDayOfWeek()).getMoneyAmount(),is(10.5));
    }

    @Test
    @DisplayName("Test createMonthsBarChart() return chart with correct money amount")
    void should_ReturnCorrectMoneyAmount_monthsBarChart() {

        //given
        List<ChartExpense> oneWeekExpenses = List.of(
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).moneyValue(50).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).moneyValue(60).build(),
                ChartExpense.builder().date(LocalDate.of(2023,9,2)).moneyValue(30).build(),
                ChartExpense.builder().date(LocalDate.of(2023,9,2)).moneyValue(40).build(),
                ChartExpense.builder().date(LocalDate.of(2023,10,3)).moneyValue(10.5).build()
        );


        //then
        BarChart<Month> actualChart = barChartCreatorStrategy.createMonthsBarChart(oneWeekExpenses);
        assertThat(actualChart.getExpenses().size(),is(3));
        assertThat(actualChart.getExpenses().get(Month.AUGUST).getMoneyAmount(),is(110.0));
        assertThat(actualChart.getExpenses().get(Month.SEPTEMBER).getMoneyAmount(),is(70.0));
        assertThat(actualChart.getExpenses().get(Month.OCTOBER).getMoneyAmount(),is(10.5));
    }

    @Test
    @DisplayName("Test createExpensiveCategoryBarChart() return chart with correct money amount")
    void should_ReturnCorrectMoneyAmount_expensiveCategoryBarChart() {

        //given
        List<ChartExpense> oneWeekExpenses = List.of(
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).expenseCategory("FOOD").moneyValue(50).build(),
                ChartExpense.builder().date(LocalDate.of(2023,8,1)).expenseCategory("FOOD").moneyValue(60).build(),
                ChartExpense.builder().date(LocalDate.of(2023,9,2)).expenseCategory("OTHER").moneyValue(30).build(),
                ChartExpense.builder().date(LocalDate.of(2023,9,2)).expenseCategory("OTHER").moneyValue(40).build(),
                ChartExpense.builder().date(LocalDate.of(2023,10,3)).expenseCategory("CAR").moneyValue(10.5).build()
        );


        //then
        BarChart<String> actualChart = barChartCreatorStrategy.createExpensiveCategoryBarChart(oneWeekExpenses);
        assertThat(actualChart.getExpenses().size(),is(3));
        assertThat(actualChart.getExpenses().get("FOOD").getMoneyAmount(),is(110.0));
        assertThat(actualChart.getExpenses().get("OTHER").getMoneyAmount(),is(70.0));
        assertThat(actualChart.getExpenses().get("CAR").getMoneyAmount(),is(10.5));
    }
}
