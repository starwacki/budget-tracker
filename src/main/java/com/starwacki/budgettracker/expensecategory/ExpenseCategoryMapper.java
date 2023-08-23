package com.starwacki.budgettracker.expensecategory;

class ExpenseCategoryMapper {

    private ExpenseCategoryMapper(){};

    static UserExpenseCategory mapDtoToEntity(String username, ExpenseCategoryDTO expenseCategoryDTO) {
        return UserExpenseCategory
                .builder()
                .categoryName(expenseCategoryDTO.categoryName())
                .username(username)
                .build();
    }

    static ExpenseCategoryDTO mapEnumToDTO(ExpenseCategory expenseCategory) {
        return new ExpenseCategoryDTO(expenseCategory.getCategoryName());
    }

}
