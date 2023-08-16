package com.starwacki.budgettracker.expense;

import org.springframework.stereotype.Component;

@Component
class ExpenseFactory {

    Expense createExpenseFromExpenseDTOAndUsername(ExpenseDTO expenseDTO, String username) {
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
