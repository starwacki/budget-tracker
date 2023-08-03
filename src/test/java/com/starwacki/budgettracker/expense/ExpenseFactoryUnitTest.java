package com.starwacki.budgettracker.expense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ExpenseFactoryUnitTest {

    private ExpenseFactory expenseFactory;

    @BeforeEach
    void setUp() {
        expenseFactory = new ExpenseFactory();
    }

    @Test
    void shouldReturn_Expense() {

        //given
        ExpenseDTO expenseDTO = ExpenseDTO
                .builder()
                .name("NAME")
                .expenseCategory(ExpenseCategory.FOOD)
                .date(LocalDate.of(2023,10,12))
                .moneyValue(200.0)
                .description("DESC")
                .time(LocalTime.of(20,12))
                .build();
        String username = "username";

        //when
        Expense expense = expenseFactory.createExpenseFromExpenseDTOAndUsername(expenseDTO,username);

        //then
        assertThat(expense.getExpenseCategory(),is(ExpenseCategory.FOOD));
        assertThat(expense.getDate(),is(LocalDate.of(2023,10,12)));
        assertThat(expense.getTime(),is(LocalTime.of(20,12)));
        assertThat(expense.getDescription(),is("DESC"));
        assertThat(expense.getName(),is("NAME"));
        assertThat(expense.getUsername(),is("username"));
        assertThat(expense.getMoneyValue(),is(200.0));
    }
}
