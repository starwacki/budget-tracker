package com.starwacki.budgettracker.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ExpenseFactory expenseFactory;

    void addNewExpenseToUser(ExpenseDTO expenseDTO,String username) {
        Expense expense = expenseFactory.createExpenseFromExpenseDTOAndUsername(expenseDTO,username);
        expenseRepository.save(expense);
    }


}
