package com.starwacki.budgettracker.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/{username}")
    public ResponseEntity<List<ExpenseDTO>> findAllExpensesByUsername(@PathVariable String username) {
        return ResponseEntity.ok(expenseService.findAllExpensesByUsername(username));
    }

    @GetMapping("/{username}/category={expenseCategory}")
    public ResponseEntity<List<ExpenseDTO>> findAllExpensesByUsernameAndExpenseCategory(@PathVariable String username,@PathVariable ExpenseCategory expenseCategory) {
        return ResponseEntity.ok(expenseService.findAllExpensesByUsernameAndExpenseCategory(username,expenseCategory));
    }

    @GetMapping("/{username}/date={date}")
    public ResponseEntity<List<ExpenseDTO>> findAllExpensesByUsernameAndDate(@PathVariable String username, @PathVariable LocalDate date) {
        return ResponseEntity.ok(expenseService.findAllExpensesByUsernameAndDate(username,date));
    }

    @GetMapping("/{username}/month={monthOrder}")
    public ResponseEntity<List<ExpenseDTO>> findAllExpensesByUsernameAndMonth(@PathVariable String username, @PathVariable int monthOrder) {
        return ResponseEntity.ok(expenseService.findAllExpensesByUsernameAndMonth(username,monthOrder));
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> addNewExpenseToUser(@RequestBody ExpenseDTO expenseDTO,@PathVariable String username) {
        expenseService.addNewExpenseToUser(expenseDTO,username);
        return ResponseEntity.noContent().build();
    }

}
