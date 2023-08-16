package com.starwacki.budgettracker.expense;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseQueryRepository expenseQueryRepository;

    @GetMapping("/v1/id/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id) {
        return expenseQueryRepository.findExpenseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/v1/id/{id}")
    public ResponseEntity<?> updateExpenseById(@PathVariable Long id, @RequestBody ExpenseDTO updatedExpenseDTO) {
        expenseService.updateExpense(id,updatedExpenseDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/{username}")
    public ResponseEntity<List<ExpenseDTO>> findAllUsernameExpenses(@PathVariable String username) {
        return ResponseEntity.ok(expenseQueryRepository.findAllUsernameExpenses(username));
    }

    @GetMapping("/v1/{username}/category/{category}")
    public ResponseEntity<List<ExpenseDTO>> findAllExpensesByUsernameAndExpenseCategory(@PathVariable String username,@PathVariable String category) {
        return ResponseEntity.ok(expenseQueryRepository.findAllUsernameExpensesWithThisExpenseCategory(username,category));
    }

    @GetMapping("/v1/{username}/date/{date}")
    public ResponseEntity<List<ExpenseDTO>> findAllDayExpenses(@PathVariable String username, @PathVariable LocalDate date) {
        return ResponseEntity.ok(expenseQueryRepository.findAllDayExpenses(username,date));
    }

    @GetMapping("/v1/{username}/week/{weekDate}")
    public ResponseEntity<List<ExpenseDTO>> findAllWeekExpenses(@PathVariable String username, @PathVariable LocalDate weekDate) {
        return ResponseEntity.ok(expenseQueryRepository.findAllWeekExpenses(username, weekDate.with(DayOfWeek.MONDAY), weekDate.with(DayOfWeek.SUNDAY)));
    }

    @GetMapping("/v1/{username}/month/{month}")
    public ResponseEntity<List<ExpenseDTO>> findAllMonthExpenses(@PathVariable String username, @PathVariable int month) {
        return ResponseEntity.ok(expenseQueryRepository.findAllMonthExpenses(username,month));
    }

    @GetMapping("/v1/{username}/year/{year}")
    public ResponseEntity<List<ExpenseDTO>> findAllYearExpenses(@PathVariable String username, @PathVariable int year) {
        return ResponseEntity.ok(expenseQueryRepository.findAllYearExpenses(username,year));
    }

    @GetMapping(value = "/v1/{username}/period",params = {"from","to"})
    public ResponseEntity<List<ExpenseDTO>> findAllPeriodExpenses(@PathVariable String username, @RequestParam LocalDate from, @RequestParam LocalDate to) {
        return ResponseEntity.ok(expenseQueryRepository.findAllPeriodExpenses(username,from,to));
    }

    @PostMapping("/v1/{username}")
    public ResponseEntity<?> addNewExpenseToUser(@RequestBody @Valid ExpenseDTO expenseDTO,@PathVariable String username) {
        expenseService.addNewExpenseToUser(expenseDTO,username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
