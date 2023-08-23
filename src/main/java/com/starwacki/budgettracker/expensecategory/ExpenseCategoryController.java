package com.starwacki.budgettracker.expensecategory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories/v1")
public class ExpenseCategoryController implements ExpenseCategoryOperations {

    private final ExpenseCategoryService expenseCategoryService;

    private final ExpenseCategoryQueryRepository expenseCategoryQueryRepository;

    @Override
    public ResponseEntity<List<ExpenseCategoryDTO>> getAllCategoriesBelongingToUser( String username) {
        return ResponseEntity.ok(expenseCategoryService.getAllCategoriesBelongingToUser(username));
    }

    @Override
    public ResponseEntity<Void> addNewUserExpenseCategory(String username, ExpenseCategoryDTO expenseCategoryDTO) {
        expenseCategoryService.addNewUserExpenseCategory(username,expenseCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<ExpenseCategoryDTO>> getAllUserExpenseCategories( String username) {
        return ResponseEntity.ok(expenseCategoryQueryRepository.findAllExpenseCategoriesCreatedByUser(username));
    }



}
