package com.starwacki.budgettracker.expense;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.time.LocalDate;
import java.util.List;

interface ExpenseQueryRepository extends Repository<Expense,Long> {

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.name,e.description, e.expenseCategory, e.date, e.time,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE e.username = :username " +
            "ORDER BY e.date DESC, e.time DESC")
    List<ExpenseDTO> findAllUsernameExpenses(String username);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.name,e.description, e.expenseCategory, e.date, e.time,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE e.username = :username AND e.expenseCategory = :expenseCategory " +
            "ORDER BY e.date DESC, e.time DESC")
    List<ExpenseDTO> findAllUsernameExpensesWithThisExpenseCategory(String username, ExpenseCategory expenseCategory);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.name,e.description, e.expenseCategory, e.date, e.time,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE (e.username = :username AND e.date = :date) " +
            "ORDER BY e.date DESC, e.time DESC")
    List<ExpenseDTO> findAllDayExpenses(String username, LocalDate date);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.name,e.description, e.expenseCategory, e.date, e.time,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE (e.username = :username AND e.date >= :startOfWeek AND e.date <= :endOfWeek ) " +
            "ORDER BY e.date DESC, e.time DESC")
    List<ExpenseDTO> findAllWeekExpenses(String username,  LocalDate startOfWeek, LocalDate endOfWeek);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.name,e.description, e.expenseCategory, e.date, e.time,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE e.username = :username AND MONTH(e.date) = :month " +
            "ORDER BY e.date DESC, e.time DESC")
    List<ExpenseDTO> findAllMonthExpenses(String username, int month);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.name,e.description, e.expenseCategory, e.date, e.time,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE e.username = :username AND FUNCTION('YEAR', e.date) = :year " +
            "ORDER BY e.date DESC, e.time DESC")
    List<ExpenseDTO> findAllYearExpenses(String username, int year);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.name,e.description, e.expenseCategory, e.date, e.time,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE (e.username = :username AND e.date >= :startDate AND e.date <= :endDate ) " +
            "ORDER BY e.date DESC, e.time DESC")
    List<ExpenseDTO> findAllPeriodExpenses(String username, LocalDate startDate, LocalDate endDate);

}
