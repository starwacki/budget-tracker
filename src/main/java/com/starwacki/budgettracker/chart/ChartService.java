package com.starwacki.budgettracker.chart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
@RequiredArgsConstructor
class ChartService {

    private final ChartExpenseQueryRepository chartExpenseQueryRepository;
    private final ChartFactory chartFactory;
    private final ChartMapper chartMapper;


    ChartDTO<String> getPieChartOfAllUsernameCategoriesExpenses(String username) {
        List<ChartExpense> expenses = chartExpenseQueryRepository.findAllUsernameChartExpenses(username);
        Chart<String> chart = chartFactory.getPieChart(expenses);
        return chartMapper.mapChartToChartDTO(chart);
    }

    ChartDTO<String> getPieChartOfUserWeekCategoriesExpenses(String username, LocalDate date) {
        LocalDate startOfWeekDate = date.with(DayOfWeek.MONDAY);
        LocalDate endOfWeekDate = date.with(DayOfWeek.SUNDAY);
        List<ChartExpense> expenses = chartExpenseQueryRepository.findAllWeekChartExpenses(username,startOfWeekDate,endOfWeekDate);
        Chart<String> chart = chartFactory.getPieChart(expenses);
        return chartMapper.mapChartToChartDTO(chart);
    }

    ChartDTO<String> getPieChartOfUserMonthCategoriesExpenses(String username, int month, int year) {
        List<ChartExpense> expenses = chartExpenseQueryRepository.findAllMonthChartExpensesInGivenYear(username,month,year);
        Chart<String> chart = chartFactory.getPieChart(expenses);
        return chartMapper.mapChartToChartDTO(chart);
    }

    ChartDTO<String> getPieChartOfUserYearCategoriesExpenses(String username, int year) {
        List<ChartExpense> expenses = chartExpenseQueryRepository.findAllYearChartExpenses(username,year);
        Chart<String> chart = chartFactory.getPieChart(expenses);
        return chartMapper.mapChartToChartDTO(chart);
    }

    ChartDTO<String> getPieChartOfUserPeriodCategoriesExpenses(String username,LocalDate startDate, LocalDate endDate) {
        List<ChartExpense> expenses = chartExpenseQueryRepository.findAllPeriodChartExpenses(username,startDate,endDate);
        Chart<String> chart = chartFactory.getPieChart(expenses);
        return chartMapper.mapChartToChartDTO(chart);
    }

    ChartDTO<DayOfWeek> getBarChartOfUserExpensesInWeek(String username, LocalDate date) {
        LocalDate startOfTheWeek = date.with(DayOfWeek.MONDAY);
        LocalDate endOfTheWeek = date.with(DayOfWeek.SUNDAY);
        List<ChartExpense> expenses = chartExpenseQueryRepository.findAllWeekChartExpenses(username,startOfTheWeek,endOfTheWeek);
        BarChart<DayOfWeek> chart = (BarChart<DayOfWeek>) chartFactory.getBarChart(ChartFactory.BarChartType.DAILY,expenses);
        return chartMapper.mapChartToChartDTO(chart);
    }

    ChartDTO<Month> getBarChartOfUserExpensesInYear(String username,int year) {
        List<ChartExpense> expenses = chartExpenseQueryRepository.findAllYearChartExpenses(username,year);
        BarChart<Month> chart = (BarChart<Month>) chartFactory.getBarChart(ChartFactory.BarChartType.MONTHLY,expenses);
        return chartMapper.mapChartToChartDTO(chart);
    }


}
