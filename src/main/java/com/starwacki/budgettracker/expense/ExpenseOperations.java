package com.starwacki.budgettracker.expense;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "expense-operations")
interface ExpenseOperations {

    @GetMapping("/id/{id}")
    ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable Long id);

    @PutMapping("/id/{id}")
    ResponseEntity<Void> updateExpenseById(@PathVariable Long id, @RequestBody ExpenseDTO updatedExpenseDTO);

    @GetMapping("/{username}")
    ResponseEntity<List<ExpenseDTO>> findAllUsernameExpenses(@PathVariable String username);

    @GetMapping("/{username}/category/{category}")
    ResponseEntity<List<ExpenseDTO>> findAllExpensesByUsernameAndExpenseCategory(@PathVariable String username,@PathVariable String category);

    @GetMapping("/{username}/date/{date}")
    ResponseEntity<List<ExpenseDTO>> findAllDayExpenses(@PathVariable String username, @PathVariable LocalDate date);

    @GetMapping("/{username}/week/{weekDate}")
    ResponseEntity<List<ExpenseDTO>> findAllWeekExpenses(@PathVariable String username, @PathVariable LocalDate weekDate);

    @GetMapping("/{username}/month/{month}")
    ResponseEntity<List<ExpenseDTO>> findAllMonthExpenses(@PathVariable String username, @PathVariable int month);

    @GetMapping("/{username}/year/{year}")
    ResponseEntity<List<ExpenseDTO>> findAllYearExpenses(@PathVariable String username, @PathVariable int year);

    @GetMapping(value = "/{username}/period",params = {"from","to"})
    ResponseEntity<List<ExpenseDTO>> findAllPeriodExpenses(@PathVariable String username, @RequestParam LocalDate from, @RequestParam LocalDate to);

    @PostMapping("/{username}")
    ResponseEntity<Void> addNewExpenseToUser(@RequestBody @Valid ExpenseDTO expenseDTO, @PathVariable String username);

}
