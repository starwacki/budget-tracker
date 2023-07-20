package com.starwacki.budgettracker.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

   List<ExpenseDTO> findAllExpensesByUsername(String username) {
        return expenseRepository
                .findAllByUsername(username)
                .stream()
                .map(expenseMapper::mapEntityToDTO)
                .toList();
    }

    void addNewExpenseToUser(ExpenseDTO expenseDTO,String username) {
        Expense expense = expenseMapper.mapDTOToEntity(expenseDTO);
        expense.setUsername(username);
        expenseRepository.save(expense);
    }

    List<ExpenseDTO> findAllExpensesByUsernameAndExpenseCategory(String username, ExpenseCategory expenseCategory) {
        return expenseRepository
                .findAllByUsernameAndExpenseCategory(username,expenseCategory)
                .stream()
                .map(expenseMapper::mapEntityToDTO)
                .toList();
    }

    List<ExpenseDTO> findAllExpensesByUsernameAndDate(String username, LocalDate date) {
        return expenseRepository.findAllByUsernameAndDate(username,date)
                .stream()
                .map(expenseMapper::mapEntityToDTO)
                .toList();
    }

    List<ExpenseDTO> findAllExpensesByUsernameAndMonth(String username, int monthOrder) {
        return expenseRepository.findAllByUsernameAndMonth(username,monthOrder)
                .stream()
                .map(expenseMapper::mapEntityToDTO)
                .toList();
    }


}
