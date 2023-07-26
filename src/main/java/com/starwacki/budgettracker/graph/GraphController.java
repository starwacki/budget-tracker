package com.starwacki.budgettracker.graph;

import com.starwacki.budgettracker.expense.ExpenseCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/graph")
public class ExpenseGraphController {

    private final ExpenseGraphService expenseGraphService;

    @GetMapping("/{username}")
    public ResponseEntity<GraphOfExpenses> getAllExpensesGraphByUsername(@PathVariable String username) {
        return ResponseEntity.ok(expenseGraphService.getAllExpensesGraphByUsername(username));
    }

    @GetMapping("/{username}/category={expenseCategory}")
    public ResponseEntity<GraphOfExpenses>  getAllExpensesGraphByUsernameAndExpenseCategory(@PathVariable String username, @PathVariable ExpenseCategory expenseCategory) {
        return ResponseEntity.ok(expenseGraphService.getAllExpensesGraphByUsernameAndExpenseCategory(username,expenseCategory));
    }

    @GetMapping("/{username}/date={date}")
    public ResponseEntity<GraphOfExpenses> getAllExpensesGraphByUsernameAndDate(@PathVariable String username, @PathVariable LocalDate date) {
        return ResponseEntity.ok(expenseGraphService.getAllExpensesGraphByUsernameAndDate(username,date));
    }

    @GetMapping("/{username}/month={monthOrder}")
    public ResponseEntity<GraphOfExpenses> getAllExpensesGraphByUsernameAndMonth(@PathVariable String username, @PathVariable int monthOrder) {
        return ResponseEntity.ok(expenseGraphService.getAllExpensesGraphByUsernameAndMonth(username,monthOrder));
    }

}
