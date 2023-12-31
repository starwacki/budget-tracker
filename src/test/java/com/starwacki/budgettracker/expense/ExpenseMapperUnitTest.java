package com.starwacki.budgettracker.expense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ExpenseMapperUnitTest {

    @Test
    void shouldReturn_Expense() {

        //given
        ExpenseDTO expenseDTO = ExpenseDTO
                .builder()
                .name("NAME")
                .expenseCategory("FOOD")
                .date(LocalDate.of(2023,10,12))
                .moneyValue(200.0)
                .description("DESC")
                .time(LocalTime.of(20,12))
                .build();
        String username = "username";

        //when
        Expense expense = ExpenseMapper.mapExpenseDtoAndUsernameToExpense(expenseDTO,username);

        //then
        assertThat(expense.getExpenseCategory(),is("FOOD"));
        assertThat(expense.getExpenseDate(),is(LocalDate.of(2023,10,12)));
        assertThat(expense.getExpenseTime(),is(LocalTime.of(20,12)));
        assertThat(expense.getDescription(),is("DESC"));
        assertThat(expense.getName(),is("NAME"));
        assertThat(expense.getUsername(),is("username"));
        assertThat(expense.getMoneyValue(),is(200.0));
    }
}
