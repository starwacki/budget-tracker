package com.starwacki.budgettracker.expensecategory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    private final ExpenseCategoryQueryRepository expenseCategoryQueryRepository;

    @GetMapping("/v1/{username}")
    public ResponseEntity<List<ExpenseCategoryDTO>> getAllCategoriesBelongingToUser(@PathVariable String username) {
        return ResponseEntity.ok(expenseCategoryService.getAllCategoriesBelongingToUser(username));
    }

    @PostMapping("/v1/{username}")
    public ResponseEntity<?> addNewUserExpenseCategory(@PathVariable String username, @Valid @RequestBody ExpenseCategoryDTO expenseCategoryDTO) {
        expenseCategoryService.addNewUserExpenseCategory(username,expenseCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/v1/user/{username}")
    public ResponseEntity<List<ExpenseCategoryDTO>> getAllUserExpenseCategories(@PathVariable String username) {
        return ResponseEntity.ok(expenseCategoryQueryRepository.findAllExpenseCategoriesCreatedByUser(username));
    }



}
