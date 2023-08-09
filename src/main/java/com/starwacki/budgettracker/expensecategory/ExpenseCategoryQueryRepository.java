package com.starwacki.budgettracker.expensecategory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

interface ExpenseCategoryQueryRepository extends Repository<UserExpenseCategory,Long> {

    @Query("SELECT NEW com.starwacki.budgettracker.expensecategory.ExpenseCategoryDTO( u.categoryName ) " +
            "FROM UserExpenseCategory u " +
            "WHERE u.username = :username "  +
            "ORDER BY u.categoryName")
    List<ExpenseCategoryDTO> findAllExpenseCategoriesCreatedByUser(String username);
}
