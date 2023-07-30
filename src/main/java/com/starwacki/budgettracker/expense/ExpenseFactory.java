package com.starwacki.budgettracker.expense;

final class ExpenseFactory {

    private ExpenseFactory() {}

    public static Expense createExpenseFromExpenseDTOAndUsername(ExpenseDTO expenseDTO, String username) {
        return Expense
                .builder()
                .username(username)
                .name(expenseDTO.name())
                .moneyValue(expenseDTO.moneyValue())
                .description(expenseDTO.description())
                .expenseCategory(expenseDTO.expenseCategory())
                .date(expenseDTO.date())
                .time(expenseDTO.time())
                .build();
    }
}
