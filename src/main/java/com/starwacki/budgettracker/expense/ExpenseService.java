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

    private final ExpenseQueryRepository expenseQueryRepository;

   List<ExpenseDTO> findAllExpensesByUsername(String username) {
        return expenseQueryRepository.findAllUsernameExpenses(username);
    }

    void addNewExpenseToUser(ExpenseDTO expenseDTO,String username) {
        Expense expense = createExpenseFromDTOAndName(expenseDTO,username);
        expenseRepository.save(expense);
    }


    List<ExpenseDTO> findAllExpensesByUsernameAndExpenseCategory(String username, ExpenseCategory expenseCategory) {
        return expenseQueryRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory);
    }

    List<ExpenseDTO> findAllExpensesByUsernameAndDate(String username, LocalDate date) {
        return expenseQueryRepository.findAllDayExpenses(username,date);
    }

    List<ExpenseDTO> findAllExpensesByUsernameAndMonth(String username, int monthOrder) {
        return expenseQueryRepository.findAllMonthExpenses(username,monthOrder);
    }

    List<ExpenseDTO> findAllWeekExpensesByUsernameAndDate(String username, LocalDate date) {
        LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = date.with(DayOfWeek.SUNDAY);
        return expenseQueryRepository.findAllWeekExpenses(username,startOfWeek,endOfWeek);
    }

    List<ExpenseDTO> findAllExpensesByUsernameAndYear(String username, int year) {
       return expenseQueryRepository.findAllYearExpenses(username,year);
    }
    
   List<ExpenseDTO> findAllPeriodExpenses(String username, LocalDate startDate, LocalDate endDate) {
       return expenseQueryRepository.findAllPeriodExpenses(username,startDate,endDate);
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
