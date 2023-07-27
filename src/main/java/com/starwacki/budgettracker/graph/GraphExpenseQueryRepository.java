package com.starwacki.budgettracker.graph;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.time.LocalDate;
import java.util.List;

interface GraphExpenseQueryRepository extends Repository<GraphExpense,Long> {

    @Query("SELECT g " +
            "FROM GraphExpense g " +
            "WHERE g.username = :username")
    List<GraphExpense> findAllUsernameGraphExpenses(String username);

    @Query("SELECT g " +
            "FROM GraphExpense g " +
            "WHERE g.username = :username AND g.expenseCategory = :expenseCategory ")
    List<GraphExpense> findAllUsernameGraphExpensesWithThisExpenseCategory(String username, String expenseCategory);

    @Query("SELECT g " +
            "FROM GraphExpense g " +
            "WHERE (g.username = :username AND g.date = :date )")
    List<GraphExpense> findAllDayGraphExpenses(String username, LocalDate date);

    @Query("SELECT g " +
            "FROM GraphExpense g " +
            "WHERE (g.username = :username AND g.date >= :startOfWeek AND g.date <= :endOfWeek )")
    List<GraphExpense> findAllWeekGraphExpenses(String username,  LocalDate startOfWeek, LocalDate endOfWeek);

    @Query("SELECT g " +
            "FROM GraphExpense g " +
            "WHERE g.username = :username AND MONTH(g.date) = :month")
    List<GraphExpense> findAllMonthGraphExpenses(String username, int month);

    @Query("SELECT g " +
            "FROM GraphExpense g " +
            "WHERE g.username = :username AND FUNCTION('YEAR', g.date) = :year")
    List<GraphExpense> findAllYearGraphExpenses(String username, int year);

    @Query("SELECT g " +
            "FROM GraphExpense g " +
            "WHERE (g.username = :username AND g.date >= :startDate AND g.date <= :endDate )")
    List<GraphExpense> findAllPeriodGraphExpenses(String username, LocalDate startDate, LocalDate endDate);
}
