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
            "WHERE (c.username = :username AND c.expenseDate = :expenseDate )")
    List<ChartExpense> findAllDayChartExpenses(String username, LocalDate expenseDate);

    @Query("SELECT c " +
            "FROM ChartExpense c " +
            "WHERE (c.username = :username AND c.expenseDate >= :startOfWeek AND c.expenseDate <= :endOfWeek )")
    List<ChartExpense> findAllWeekChartExpenses(String username, LocalDate startOfWeek, LocalDate endOfWeek);

    @Query("SELECT c " +
            "FROM ChartExpense c " +
            "WHERE c.username = :username AND MONTH(c.expenseDate) = :month")
    List<ChartExpense> findAllMonthChartExpenses(String username, int month);

    @Query("SELECT c " +
            "FROM ChartExpense c " +
            "WHERE c.username = :username AND FUNCTION('YEAR', c.expenseDate) = :year")
    List<ChartExpense> findAllYearChartExpenses(String username, int year);

    @Query("SELECT c " +
            "FROM ChartExpense c " +
            "WHERE (c.username = :username AND c.expenseDate >= :startDate AND c.expenseDate <= :endDate )")
    List<ChartExpense> findAllPeriodChartExpenses(String username, LocalDate startDate, LocalDate endDate);
}
