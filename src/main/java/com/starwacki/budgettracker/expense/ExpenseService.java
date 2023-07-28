package com.starwacki.budgettracker.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
class ExpenseService {

    private final ExpenseRepository expenseRepository;

    void addNewExpenseToUser(ExpenseDTO expenseDTO,String username) {
        Expense expense = createExpenseFromDTOAndName(expenseDTO,username);
        expenseRepository.save(expense);
    }

    private Expense createExpenseFromDTOAndName(ExpenseDTO expenseDTO,String username) {
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
