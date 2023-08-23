package com.starwacki.budgettracker.expense;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
class ExpenseQueryRepositoryUnitTest {

    @Autowired
    private ExpenseQueryRepository expenseQueryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllUsernameExpenses() when user hasn't any expense in database")
    void should_ReturnEmptyList_usernameQuery() {

        //given
        String username = "user_with_zero_expenses";

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllUsernameExpenses() when user has 4 expenses in database")
    void should_ReturnListWith4Elements() {

        //given
        String username = "john_doe";

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();
        int expectedNumberOfUserExpenses = 4;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllUsernameExpenses() correct sorting by the newest")
    void should_ReturnListSortedByDate() {

        //given
        String username = "john_doe";

        //when
        List<ExpenseDTO> userExpenses = expenseQueryRepository.findAllUsernameExpenses(username);

        int expectedNumberOfUserExpenses = 4;
        int actualNumberOfUserExpenses = userExpenses.size();

        LocalDate expectedDateOfFirstElement = LocalDate.of(2023, 7, 15);
        LocalTime expectedTimeOfFistElement = LocalTime.of(15,45);

        LocalDate actualDateOfFirstElement = userExpenses.get(0).date();
        LocalTime actualTimeOfFistElement = userExpenses.get(0).time();

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
        assertEquals(expectedDateOfFirstElement,actualDateOfFirstElement);
        assertEquals(expectedTimeOfFistElement,actualTimeOfFistElement);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() when user hasn't expense with this category")
    void should_ReturnEmptyList_When_UserHasNotExpenseWithThisCategory() {

        //given
        String username = "john_doe";
        String expenseCategory = "OTHER";

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() when user has 2 expenses in this category")
    void should_Return2ElementsList_When_UserHasExpenseWithThisCategory() {

        //given
        String username = "john_doe";
        String expenseCategory = "FOOD";

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory).size();
        int expectedNumberOfUserExpenses = 2;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() correct sorting")
    void should_ReturnSortedByDateList_When_UserHasExpenseWithThisCategory() {

        //given
        String username = "john_doe";
        String expenseCategory = "FOOD";

        //when
        List<ExpenseDTO> userExpenses = expenseQueryRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory);

        int expectedNumberOfUserExpenses = 2;
        int actualNumberOfUserExpenses = userExpenses.size();

        LocalDate expectedDateOfFirstElement = LocalDate.of(2023, 7, 15);
        LocalTime expectedTimeOfFistElement = LocalTime.of(12,30);

        LocalDate actualDateOfFirstElement = userExpenses.get(0).date();
        LocalTime actualTimeOfFistElement = userExpenses.get(0).time();

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
        assertEquals(expectedDateOfFirstElement,actualDateOfFirstElement);
        assertEquals(expectedTimeOfFistElement,actualTimeOfFistElement);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllDayExpenses() when user hasn't any expense in database")
    void should_ReturnEmptyList_dateQuery() {

        //given
        String username = "user_with_zero_expenses";
        LocalDate date = LocalDate.of(2022,10,12);

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllDayExpenses(username,date).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllDayExpenses() when user hasn't any expense with this date")
    void should_ReturnEmptyList_When_UserHasNotExpensesWithThisDate() {

        //given
        String username = "john_doe";
        LocalDate dateWithoutAnyExpense = LocalDate.of(2022,10,12);

        //when
        int actualNumberOfAllUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();
        int actualNumberOfUserExpensesInThisDate = expenseQueryRepository.findAllDayExpenses(username,dateWithoutAnyExpense).size();
        int expectedNumberOfUserExpensesInThisDate = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisDate,actualNumberOfUserExpensesInThisDate);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllDayExpenses() when user has 2 expenses with this date")
    void should_Return2ElementsList_When_UserHasExpensesWithThisDate() {

        //given
        String username = "john_doe";
        LocalDate date= LocalDate.of(2023,7,15);

        //when
        int actualNumberOfUserExpensesInThisDate = expenseQueryRepository.findAllDayExpenses(username,date).size();
        int expectedNumberOfUserExpensesInThisDate = 2;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisDate,actualNumberOfUserExpensesInThisDate);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllWeekExpenses() when user hasn't any expense in database")
    void should_ReturnEmptyList_weekQuery() {

        //given
        String username = "user_with_zero_expenses";
        LocalDate mondayDate = LocalDate.of(2023,7,24);
        LocalDate sundayDate = LocalDate.of(2023,7,30);

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllWeekExpenses(username,mondayDate,sundayDate).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllWeekExpenses() when user hasn't any expense in this week")
    void should_ReturnEmptyList_When_UserHasNotExpensesInThisWeek() {

        //given
        String username = "john_doe";
        LocalDate mondayDate = LocalDate.of(2023,7,24);
        LocalDate sundayDate = LocalDate.of(2023,7,30);

        //when
        int actualNumberOfAllUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();
        int actualNumberOfUserExpensesInThisWeek = expenseQueryRepository.findAllWeekExpenses(username,mondayDate,sundayDate).size();
        int expectedNumberOfUserExpensesInThisWeek = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisWeek,actualNumberOfUserExpensesInThisWeek);
    }


    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllWeekExpenses() when user has 3 expenses in this week")
    void should_Return2ElementsList_When_UserHasExpensesInThisWeek() {

        //given
        String username = "pet_owner";
        LocalDate mondayDate = LocalDate.of(2023,7,24);
        LocalDate sundayDate = LocalDate.of(2023,7,30);

        //when
        int actualNumberOfUserExpensesInThisWeek = expenseQueryRepository.findAllWeekExpenses(username,mondayDate,sundayDate).size();
        int expectedNumberOfUserExpensesInThisWeek = 3;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisWeek,actualNumberOfUserExpensesInThisWeek);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllMonthExpenses() when user hasn't any expense in database")
    void should_ReturnEmptyList_monthQuery() {

        //given
        String username = "user_with_zero_expenses";
        int monthOrdinal = 12;
        int year = 2020;

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllMonthExpensesInGivenYear(username,monthOrdinal,year).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllMonthExpenses() when user hasn't any expense in this month")
    void should_ReturnEmptyList_When_UserHasNotExpensesWithThisMonth() {

        //given
        String username = "john_doe";
        int monthOrdinal = 12;
        int year = 2023;

        //when
        int actualNumberOfAllUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();
        int actualNumberOfUserExpensesInThisMonth = expenseQueryRepository.findAllMonthExpensesInGivenYear(username,monthOrdinal,year).size();
        int expectedNumberOfUserExpensesInThisMonth = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisMonth,actualNumberOfUserExpensesInThisMonth);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllMonthExpenses() when user has 3 expenses in this month")
    void should_Return3ElementsList_When_UserHas3ExpensesWithThisMonth() {

        //given
        String username = "john_doe";
        int monthOrdinal = 7;
        int year = 2023;

        //when
        int actualNumberOfUserExpensesInThisMonth = expenseQueryRepository.findAllMonthExpensesInGivenYear(username,monthOrdinal,year).size();
        int expectedNumberOfUserExpensesInThisMonth = 3;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisMonth,actualNumberOfUserExpensesInThisMonth);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllYearExpenses() when user hasn't any expense in database")
    void should_ReturnEmptyList_yearQuery() {

        //given
        String username = "user_with_zero_expenses";
        int year = 2023;

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllYearExpenses(username,year).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllYearExpenses() when user hasn't any expense in this year")
    void should_ReturnEmptyList_When_UserHasNotExpensesWithThisYear() {

        //given
        String username = "alice_wonder";
        int year = 2020;

        //when
        int actualNumberOfAllUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();
        int actualNumberOfUserExpensesInThisYear = expenseQueryRepository.findAllYearExpenses(username,year).size();
        int expectedNumberOfUserExpensesInThisYear = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisYear,actualNumberOfUserExpensesInThisYear);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAlYearExpenses() when user has 1 expenses in this year")
    void should_Return1ElementsList_When_UserHas1ExpensesWithThisYear() {

        //given
        String username = "alice_wonder";
        int year = 2021;

        //when
        int actualNumberOfUserExpensesInThisYear = expenseQueryRepository.findAllYearExpenses(username,year).size();
        int expectedNumberOfUserExpensesInThisYear = 1;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisYear,actualNumberOfUserExpensesInThisYear);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllPeriodExpenses() when user hasn't any expense in database")
    void should_ReturnEmptyList_PeriodQuery() {

        //given
        String username = "user_with_zero_expenses";
        LocalDate startDate = LocalDate.of(2023,10,12);
        LocalDate endDate = LocalDate.of(2024,10,19);

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllPeriodExpenses(username,startDate,endDate).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllPeriodExpenses() when user hasn't any expense in this period")
    void should_ReturnEmptyList_When_UserHasNotExpensesInThisPeriod() {

        //given
        String username = "alice_wonder";
        LocalDate startDate = LocalDate.of(2023,10,12);
        LocalDate endDate = LocalDate.of(2024,10,19);

        //when
        int actualNumberOfAllUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();
        int actualNumberOfUserExpensesInThisPeriod = expenseQueryRepository.findAllPeriodExpenses(username,startDate,endDate).size();
        int expectedNumberOfUserExpensesInThisPeriod = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisPeriod,actualNumberOfUserExpensesInThisPeriod);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findAllPeriodExpenses() when user has 1 expense in this period")
    void should_Return1ElementsList_When_UserHas1ExpensesWithThisPeriod() {

        //given
        String username = "alice_wonder";
        LocalDate startDate = LocalDate.of(2023,7,12);
        LocalDate endDate = LocalDate.of(2024,10,19);

        //when
        int actualNumberOfUserExpensesInThisPeriod = expenseQueryRepository.findAllPeriodExpenses(username,startDate,endDate).size();
        int expectedNumberOfUserExpensesInThisPeriod = 1;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisPeriod,actualNumberOfUserExpensesInThisPeriod);
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @DisplayName("Test findExpenseById() return expense  when expense exist")
    void should_ReturnExpense() {

        //given
        String username = "username";
        Expense expense = ExpenseMapper.mapExpenseDtoAndUsernameToExpense(
                ExpenseDTO.builder().name("NAME").build(),username
        );
        Long id = expenseRepository.save(expense).getId();

        //when
        ExpenseDTO expectedExpense = expenseQueryRepository.findById(id).get();

        assertThat(expectedExpense.name(),is(equalTo(expense.getName())));

    }

    @Test
    @DisplayName("Test findExpenseById() return Optional  when expense no exist")
    void should_ReturnOptional() {

        //given
        Long id = 0L;

        //when
        Optional<ExpenseDTO> expectedExpense = expenseQueryRepository.findById(id);

        assertThat(expectedExpense,is(Optional.empty()));
    }

}
