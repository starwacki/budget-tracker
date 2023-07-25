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
    private final ExpenseMapper expenseMapper;

   List<ExpenseDTO> findAllExpensesByUsername(String username) {
        return expenseRepository
                .findAllUsernameExpenses(username)
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
                .findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory)
                .stream()
                .map(expenseMapper::mapEntityToDTO)
                .toList();
    }

    List<ExpenseDTO> findAllExpensesByUsernameAndDate(String username, LocalDate date) {
        return expenseRepository.findAllDayExpenses(username,date)
                .stream()
                .map(expenseMapper::mapEntityToDTO)
                .toList();
    }

    List<ExpenseDTO> findAllExpensesByUsernameAndMonth(String username, int monthOrder) {
        return expenseRepository.findAllMonthExpenses(username,monthOrder)
                .stream()
                .map(expenseMapper::mapEntityToDTO)
                .toList();
    }

    List<ExpenseDTO> findAllWeekExpensesByUsernameAndDate(String username, LocalDate date) {
        LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = date.with(DayOfWeek.SUNDAY);
        return expenseRepository.findAllWeekExpenses(username,startOfWeek,endOfWeek)
                .stream()
                .map(expenseMapper::mapEntityToDTO)
                .toList();
    }

    List<ExpenseDTO> findAllExpensesByUsernameAndYear(String username, int year) {
       return expenseRepository.findAllYearExpenses(username,year)
               .stream()
               .map(expenseMapper::mapEntityToDTO)
               .toList();
    }
    
    public List<ExpenseDTO> findAllPeriodExpenses(String username, LocalDate startDate, LocalDate endDate) {
       return expenseRepository.findAllPeriodExpenses(username,startDate,endDate)
                .stream()
                .map(expenseMapper::mapEntityToDTO)
                .toList();
    }
}
