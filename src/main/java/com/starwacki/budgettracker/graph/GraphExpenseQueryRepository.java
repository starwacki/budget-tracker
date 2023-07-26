package com.starwacki.budgettracker.graph;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.List;

interface GraphExpenseQueryRepository extends Repository<GraphExpense,Long> {

    @Query("SELECT NEW GraphExpense(e.id, e.expenseCategory, e.moneyValue) FROM Expense e WHERE e.username = :username")
    List<GraphExpense> findAllUsernameExpenses(String username);

    @Query("SELECT NEW GraphExpense(e.id, e.expenseCategory, e.moneyValue) FROM Expense e WHERE e.username = :username AND e.expenseCategory = :expenseCategory ")
    List<GraphExpense> findAllUsernameExpensesWithThisExpenseCategory(String username, String expenseCategory);

    @Query("SELECT NEW GraphExpense(e.id, e.expenseCategory, e.moneyValue) FROM Expense e WHERE (e.username = :username AND e.date = :date ) ")
    List<GraphExpense> findAllDayExpenses(String username, LocalDate date);

    @Query("SELECT NEW GraphExpense(e.id, e.expenseCategory, e.moneyValue) FROM Expense e WHERE (e.username = :username AND e.date >= :startOfWeek AND e.date <= :endOfWeek )")
    List<GraphExpense> findAllWeekExpenses(String username,  LocalDate startOfWeek, LocalDate endOfWeek);

    @Query("SELECT NEW GraphExpense(e.id, e.expenseCategory, e.moneyValue) FROM Expense e WHERE e.username = :username AND MONTH(e.date) = :month")
    List<GraphExpense> findAllMonthExpenses(String username, int month);

    @Query("SELECT NEW GraphExpense(e.id, e.expenseCategory, e.moneyValue) FROM Expense e WHERE e.username = :username AND FUNCTION('YEAR', e.date) = :year")
    List<GraphExpense> findAllYearExpenses(String username, int year);

    @Query("SELECT NEW GraphExpense(e.id, e.expenseCategory, e.moneyValue) FROM Expense e WHERE (e.username = :username AND e.date >= :startDate AND e.date <= :endDate )")
    List<GraphExpense> findAllPeriodExpenses(String username, LocalDate startDate, LocalDate endDate);
}
