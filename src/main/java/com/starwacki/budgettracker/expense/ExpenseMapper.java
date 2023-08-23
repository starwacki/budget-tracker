package com.starwacki.budgettracker.expense;


final class ExpenseMapper {

    private ExpenseMapper() {};

    static Expense mapExpenseDtoAndUsernameToExpense(ExpenseDTO expenseDTO, String username) {
        return Expense
                .builder()
                .username(username)
                .name(expenseDTO.name())
                .moneyValue(expenseDTO.moneyValue())
                .description(expenseDTO.description())
                .expenseCategory(expenseDTO.expenseCategory())
                .expenseDate(expenseDTO.date())
                .expenseTime(expenseDTO.time())
                .build();
    }
}
