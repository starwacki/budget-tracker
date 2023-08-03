package com.starwacki.budgettracker.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ExpenseFactory expenseFactory;

    void addNewExpenseToUser(ExpenseDTO expenseDTO,String username) {
        Expense expense = expenseFactory.createExpenseFromExpenseDTOAndUsername(expenseDTO,username);
        expenseRepository.save(expense);
    }


    void updateExpense(Long id, ExpenseDTO updatedExpenseDTO) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isEmpty())
            throw new ResourceNotFoundException(ExpenseService.class,"Expense not found");
        expense.get().updateObject(updatedExpenseDTO);
        expenseRepository.save(expense.get());
    }


}
