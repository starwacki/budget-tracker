package com.starwacki.budgettracker.expensecategory;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

record ExpenseCategoryDTO(
      @NotBlank @Length(min = 3,max = 40) String categoryName
) {
}
