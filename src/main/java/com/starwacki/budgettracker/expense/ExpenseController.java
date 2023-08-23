package com.starwacki.budgettracker.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses/v1")
class ExpenseController implements ExpenseOperations {

    private final ExpenseService expenseService;
    private final ExpenseQueryRepository expenseQueryRepository;

    @Override
    public ResponseEntity<ExpenseDTO> getExpenseById(Long id) {
        return expenseQueryRepository.findById(id)
                .map(expenseDTO -> ResponseEntity.ok(expenseDTO))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> updateExpenseById(Long id, ExpenseDTO updatedExpenseDTO) {
        expenseService.updateExpense(id,updatedExpenseDTO);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteExpenseById(Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ExpenseDTO>> findAllUsernameExpenses(String username) {
        return ResponseEntity.ok(expenseQueryRepository.findAllUsernameExpenses(username));
    }

    @Override
    public ResponseEntity<List<ExpenseDTO>> findAllExpensesByUsernameAndExpenseCategory(String username,String category) {
        return ResponseEntity.ok(expenseQueryRepository.findAllUsernameExpensesWithThisExpenseCategory(username,category));
    }

    @Override
    public ResponseEntity<List<ExpenseDTO>> findAllDayExpenses(String username,LocalDate date) {
        return ResponseEntity.ok(expenseQueryRepository.findAllDayExpenses(username,date));
    }

    @Override
    public ResponseEntity<List<ExpenseDTO>> findAllWeekExpenses(String username, LocalDate weekDate) {
        return ResponseEntity.ok(expenseQueryRepository.findAllWeekExpenses(username, weekDate.with(DayOfWeek.MONDAY), weekDate.with(DayOfWeek.SUNDAY)));
    }

    @Override
    public ResponseEntity<List<ExpenseDTO>> findAllMonthExpenses(String username, int month, int year) {
        return ResponseEntity.ok(expenseQueryRepository.findAllMonthExpensesInGivenYear(username, month, year));
    }

    @Override
    public ResponseEntity<List<ExpenseDTO>> findAllYearExpenses(String username, int year) {
        return ResponseEntity.ok(expenseQueryRepository.findAllYearExpenses(username,year));
    }

    @Override
    public ResponseEntity<List<ExpenseDTO>> findAllPeriodExpenses(String username, LocalDate from, LocalDate to) {
        return ResponseEntity.ok(expenseQueryRepository.findAllPeriodExpenses(username,from,to));
    }

    @Override
    public ResponseEntity<Void> addNewExpenseToUser(ExpenseDTO expenseDTO,String username) {
        expenseService.addNewExpenseToUser(expenseDTO,username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
