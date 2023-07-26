package com.starwacki.budgettracker.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface ExpenseRepository extends JpaRepository<Expense,Long> {

    @Query("SELECT e FROM Expense e WHERE e.username = :username ORDER BY e.date DESC, e.time DESC")
    List<Expense> findAllUsernameExpenses(String username);

    @Query("SELECT e FROM Expense e WHERE e.username = :username AND e.expenseCategory = :expenseCategory ORDER BY e.date DESC, e.time DESC")
    List<Expense> findAllUsernameExpensesWithThisExpenseCategory(String username, ExpenseCategory expenseCategory);

    @Query("SELECT e FROM Expense e WHERE (e.username = :username AND e.date = :date) ORDER BY e.date DESC, e.time DESC")
    List<Expense> findAllDayExpenses(String username, LocalDate date);

    @Query("SELECT e FROM Expense e WHERE (e.username = :username AND e.date >= :startOfWeek AND e.date <= :endOfWeek ) ORDER BY e.date DESC, e.time DESC")
    List<Expense> findAllWeekExpenses(String username,  LocalDate startOfWeek, LocalDate endOfWeek);

    @Query("SELECT e FROM Expense e WHERE e.username = :username AND MONTH(e.date) = :month ORDER BY e.date DESC, e.time DESC")
    List<Expense> findAllMonthExpenses(String username, int month);

    @Query("SELECT e FROM Expense e WHERE e.username = :username AND FUNCTION('YEAR', e.date) = :year ORDER BY e.date DESC, e.time DESC")
    List<Expense> findAllYearExpenses(String username, int year);

    @Query("SELECT e FROM Expense e WHERE (e.username = :username AND e.date >= :startDate AND e.date <= :endDate ) ORDER BY e.date DESC, e.time DESC")
    List<Expense> findAllPeriodExpenses(String username, LocalDate startDate, LocalDate endDate);

}
