package com.starwacki.budgettracker.expense;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
@Validated
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/{username}")
    public ResponseEntity<List<ExpenseDTO>> findAllUsernameExpenses(@PathVariable String username) {
        return ResponseEntity.ok(expenseService.findAllExpensesByUsername(username));
    }

    @GetMapping("/{username}/category={expenseCategory}")
    public ResponseEntity<List<ExpenseDTO>> findAllExpensesByUsernameAndExpenseCategory(@PathVariable String username,@PathVariable ExpenseCategory expenseCategory) {
        return ResponseEntity.ok(expenseService.findAllExpensesByUsernameAndExpenseCategory(username,expenseCategory));
    }

    @GetMapping("/{username}/date={date}")
    public ResponseEntity<List<ExpenseDTO>> findAllDayExpenses(@PathVariable String username, @PathVariable LocalDate date) {
        return ResponseEntity.ok(expenseService.findAllExpensesByUsernameAndDate(username,date));
    }

    @GetMapping("/{username}/week={date}")
    public ResponseEntity<List<ExpenseDTO>> findAllWeekExpenses(@PathVariable String username, @PathVariable LocalDate date) {
        return ResponseEntity.ok(expenseService.findAllWeekExpensesByUsernameAndDate(username,date));
    }

    @GetMapping("/{username}/month={monthOrder}")
    public ResponseEntity<List<ExpenseDTO>> findAllMonthExpenses(@PathVariable String username, @PathVariable int monthOrder) {
        return ResponseEntity.ok(expenseService.findAllExpensesByUsernameAndMonth(username,monthOrder));
    }

    @GetMapping("/{username}/year={year}")
    public ResponseEntity<List<ExpenseDTO>> findAllYearExpenses(@PathVariable String username, @PathVariable int year) {
        return ResponseEntity.ok(expenseService.findAllExpensesByUsernameAndYear(username,year));
    }

    @GetMapping("/{username}/from={startDate}&to={endDate}")
    public ResponseEntity<List<ExpenseDTO>> findAllPeriodExpenses(@PathVariable String username, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return ResponseEntity.ok(expenseService.findAllPeriodExpenses(username,startDate,endDate));
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> addNewExpenseToUser(@RequestBody @Valid ExpenseDTO expenseDTO,@PathVariable String username) {
        expenseService.addNewExpenseToUser(expenseDTO,username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
