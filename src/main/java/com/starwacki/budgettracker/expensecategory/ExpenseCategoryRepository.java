package com.starwacki.budgettracker.expensecategory;

import org.springframework.data.jpa.repository.JpaRepository;

interface ExpenseCategoryRepository extends JpaRepository<UserExpenseCategory,Long> {

    //TODO: change this to existsByCategoryNameAndUsername
    boolean existsByCategoryName(String categoryName);

}
