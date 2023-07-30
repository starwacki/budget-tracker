package com.starwacki.budgettracker.chart;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.time.LocalDate;
import java.util.List;

interface ChartExpenseQueryRepository extends Repository<ChartExpense,Long> {

    @Query("SELECT c " +
            "FROM ChartExpense c " +
            "WHERE c.username = :username")
    List<ChartExpense> findAllUsernameChartExpenses(String username);

    @Query("SELECT c " +
            "FROM ChartExpense c " +
            "WHERE c.username = :username AND c.expenseCategory = :expenseCategory ")
    List<ChartExpense> findAllUsernameChartExpensesWithThisExpenseCategory(String username, String expenseCategory);

    @Query("SELECT c " +
            "FROM ChartExpense c " +
            "WHERE (c.username = :username AND c.date = :date )")
    List<ChartExpense> findAllDayChartExpenses(String username, LocalDate date);

    @Query("SELECT c " +
            "FROM ChartExpense c " +
            "WHERE (c.username = :username AND c.date >= :startOfWeek AND c.date <= :endOfWeek )")
    List<ChartExpense> findAllWeekChartExpenses(String username, LocalDate startOfWeek, LocalDate endOfWeek);

    @Query("SELECT c " +
            "FROM ChartExpense c " +
            "WHERE c.username = :username AND MONTH(c.date) = :month")
    List<ChartExpense> findAllMonthChartExpenses(String username, int month);

    @Query("SELECT c " +
            "FROM ChartExpense c " +
            "WHERE c.username = :username AND FUNCTION('YEAR', c.date) = :year")
    List<ChartExpense> findAllYearChartExpenses(String username, int year);

    @Query("SELECT c " +
            "FROM ChartExpense c " +
            "WHERE (c.username = :username AND c.date >= :startDate AND c.date <= :endDate )")
    List<ChartExpense> findAllPeriodChartExpenses(String username, LocalDate startDate, LocalDate endDate);
}
