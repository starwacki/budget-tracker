package com.starwacki.budgettracker.expense;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface ExpenseQueryRepository extends Repository<Expense,Long> {

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.id,e.name,e.description, e.expenseCategory, e.expenseDate, e.expenseTime,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE e.username = :username " +
            "ORDER BY e.expenseDate DESC, e.expenseTime DESC")
    List<ExpenseDTO> findAllUsernameExpenses(String username);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.id,e.name,e.description, e.expenseCategory, e.expenseDate, e.expenseTime,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE e.username = :username AND e.expenseCategory = :expenseCategory " +
            "ORDER BY e.expenseDate DESC, e.expenseTime DESC")
    List<ExpenseDTO> findAllUsernameExpensesWithThisExpenseCategory(String username, String expenseCategory);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.id,e.name,e.description, e.expenseCategory, e.expenseDate, e.expenseTime,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE (e.username = :username AND e.expenseDate = :expenseDate) " +
            "ORDER BY e.expenseDate DESC, e.expenseTime DESC")
    List<ExpenseDTO> findAllDayExpenses(String username, LocalDate expenseDate);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.id,e.name,e.description, e.expenseCategory, e.expenseDate, e.expenseTime,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE (e.username = :username AND e.expenseDate >= :startOfWeek AND e.expenseDate <= :endOfWeek ) " +
            "ORDER BY e.expenseDate DESC, e.expenseTime DESC")
    List<ExpenseDTO> findAllWeekExpenses(String username,  LocalDate startOfWeek, LocalDate endOfWeek);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.id,e.name,e.description, e.expenseCategory, e.expenseDate, e.expenseTime,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE e.username = :username AND MONTH(e.expenseDate) = :month AND YEAR(e.expenseDate) = :year " +
            "ORDER BY e.expenseDate DESC, e.expenseTime DESC")
    List<ExpenseDTO> findAllMonthExpensesInGivenYear(String username, int month, int year);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.id,e.name,e.description, e.expenseCategory, e.expenseDate, e.expenseTime,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE e.username = :username AND YEAR(e.expenseDate) = :year " +
            "ORDER BY e.expenseDate DESC, e.expenseTime DESC")
    List<ExpenseDTO> findAllYearExpenses(String username, int year);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.id,e.name,e.description, e.expenseCategory, e.expenseDate, e.expenseTime,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE (e.username = :username AND e.expenseDate >= :startDate AND e.expenseDate <= :endDate ) " +
            "ORDER BY e.expenseDate DESC, e.expenseTime DESC")
    List<ExpenseDTO> findAllPeriodExpenses(String username, LocalDate startDate, LocalDate endDate);

    @Query("SELECT NEW com.starwacki.budgettracker.expense.ExpenseDTO(e.id,e.name,e.description, e.expenseCategory, e.expenseDate, e.expenseTime,e.moneyValue) " +
            "FROM Expense e " +
            "WHERE e.id = :id")
    Optional<ExpenseDTO> findById(Long id);




}
