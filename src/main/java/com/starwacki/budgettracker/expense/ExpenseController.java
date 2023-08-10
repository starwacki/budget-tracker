package com.starwacki.budgettracker.expense;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseQueryRepository expenseQueryRepository;

    @GetMapping("/{username}")
    public ResponseEntity<List<ExpenseDTO>> findAllUsernameExpenses(@PathVariable String username) {
        return ResponseEntity.ok(expenseQueryRepository.findAllUsernameExpenses(username));
    }

    @GetMapping("/{username}/category={expenseCategory}")
    public ResponseEntity<List<ExpenseDTO>> findAllExpensesByUsernameAndExpenseCategory(@PathVariable String username,@PathVariable String expenseCategory) {
        return ResponseEntity.ok(expenseQueryRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory));
    }

    @GetMapping("/{username}/date={date}")
    public ResponseEntity<List<ExpenseDTO>> findAllDayExpenses(@PathVariable String username, @PathVariable LocalDate date) {
        return ResponseEntity.ok(expenseQueryRepository.findAllDayExpenses(username,date));
    }

    @GetMapping("/{username}/week={date}")
    public ResponseEntity<List<ExpenseDTO>> findAllWeekExpenses(@PathVariable String username, @PathVariable LocalDate date) {
        return ResponseEntity.ok(expenseQueryRepository.findAllWeekExpenses(username, date.with(DayOfWeek.MONDAY), date.with(DayOfWeek.SUNDAY)));
    }

    @GetMapping("/{username}/month={monthOrder}")
    public ResponseEntity<List<ExpenseDTO>> findAllMonthExpenses(@PathVariable String username, @PathVariable int monthOrder) {
        return ResponseEntity.ok(expenseQueryRepository.findAllMonthExpenses(username,monthOrder));
    }

    @GetMapping("/{username}/year={year}")
    public ResponseEntity<List<ExpenseDTO>> findAllYearExpenses(@PathVariable String username, @PathVariable int year) {
        return ResponseEntity.ok(expenseQueryRepository.findAllYearExpenses(username,year));
    }

    @GetMapping("/{username}/from={startDate}&to={endDate}")
    public ResponseEntity<List<ExpenseDTO>> findAllPeriodExpenses(@PathVariable String username, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return ResponseEntity.ok(expenseQueryRepository.findAllPeriodExpenses(username,startDate,endDate));
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> addNewExpenseToUser(@RequestBody @Valid ExpenseDTO expenseDTO,@PathVariable String username) {
        expenseService.addNewExpenseToUser(expenseDTO,username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id) {
        return expenseQueryRepository.findExpenseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/id={id}")
    public ResponseEntity<?> updateExpenseById(@PathVariable Long id, @RequestBody ExpenseDTO updatedExpenseDTO) {
        expenseService.updateExpense(id,updatedExpenseDTO);
        return ResponseEntity.noContent().build();
    }

}
