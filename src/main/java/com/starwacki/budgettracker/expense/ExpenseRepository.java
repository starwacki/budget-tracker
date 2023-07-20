package com.starwacki.budgettracker.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface ExpenseRepository extends JpaRepository<Expense,Long> {

    @Query("SELECT e from Expense e WHERE e.username = :username ORDER BY e.date DESC, e.time DESC")
    List<Expense> findAllByUsername(String username);

    @Query("SELECT e from Expense e WHERE e.username = :username AND e.expenseCategory = :expenseCategory ORDER BY e.date DESC, e.time DESC")
    List<Expense> findAllByUsernameAndExpenseCategory(String username, ExpenseCategory expenseCategory);

    @Query("SELECT e from Expense e WHERE (e.username = :username AND e.date = :date) ORDER BY e.date DESC, e.time DESC")
    List<Expense> findAllByUsernameAndDate(String username, LocalDate date);

    @Query("SELECT e from Expense e WHERE e.username = :username AND MONTH(e.date) = :month ORDER BY e.date DESC, e.time DESC")
    List<Expense> findAllByUsernameAndMonth(String username,  int month);

}
