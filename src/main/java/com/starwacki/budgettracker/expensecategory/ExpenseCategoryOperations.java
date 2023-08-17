package com.starwacki.budgettracker.expensecategory;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "expense-category-operations")
interface ExpenseCategoryOperations {

    @GetMapping("/{username}")
    ResponseEntity<List<ExpenseCategoryDTO>> getAllCategoriesBelongToUser(@PathVariable String username);

    @PostMapping("/{username}")
    ResponseEntity<?> addNewUserExpenseCategory(@PathVariable String username, @Valid @RequestBody ExpenseCategoryDTO expenseCategoryDTO);

    @GetMapping("/user-categories/{username}")
    ResponseEntity<List<ExpenseCategoryDTO>> getAllUserExpenseCategories(@PathVariable String username);
}
