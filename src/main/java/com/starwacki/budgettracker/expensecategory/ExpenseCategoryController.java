package com.starwacki.budgettracker.expensecategory;

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

    @GetMapping("/{username}")
    public ResponseEntity<List<ExpenseCategoryDTO>> getAllCategoriesBelongingToUser(@PathVariable String username) {
        return ResponseEntity.ok(expenseCategoryService.getAllCategoriesBelongingToUser(username));
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> addNewUserExpenseCategory(@PathVariable String username, @RequestBody ExpenseCategoryDTO expenseCategoryDTO) {
        expenseCategoryService.addNewUserExpenseCategory(username,expenseCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ExpenseCategoryDTO>> getAllUserExpenseCategories(@PathVariable String username) {
        return ResponseEntity.ok(expenseCategoryQueryRepository.findAllExpenseCategoriesCreatedByUser(username));
    }



}
