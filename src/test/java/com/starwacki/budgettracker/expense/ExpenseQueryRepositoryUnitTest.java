package com.starwacki.budgettracker.expense;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@ActiveProfiles("test")
class ExpenseQueryRepositoryUnitTest {

    @Autowired
    private ExpenseQueryRepository expenseQueryRepository;

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllUsernameExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_usernameQuery() {

        //given
        String username = "user_with_zero_expenses";

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllUsernameExpenses() when user has 4 expenses in database")
    void Should_ReturnListWith4Elements() {

        //given
        String username = "john_doe";

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();
        int expectedNumberOfUserExpenses = 4;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllUsernameExpenses() correct sorting by the newest")
    void Should_ReturnListSortedByDate() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() when user hasn't expense with this category")
    void Should_ReturnEmptyList_When_UserHasNotExpenseWithThisCategory() {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.OTHER;

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() when user has 2 expenses in this category")
    void Should_Return2ElementsList_When_UserHasExpenseWithThisCategory() {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.FOOD;

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory).size();
        int expectedNumberOfUserExpenses = 2;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() correct sorting")
    void Should_ReturnSortedByDateList_When_UserHasExpenseWithThisCategory() {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.FOOD;

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllDayExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_dateQuery() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllDayExpenses() when user hasn't any expense with this date")
    void Should_ReturnEmptyList_When_UserHasNotExpensesWithThisDate() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllDayExpenses() when user has 2 expenses with this date")
    void Should_Return2ElementsList_When_UserHasExpensesWithThisDate() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllWeekExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_weekQuery() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllWeekExpenses() when user hasn't any expense in this week")
    void Should_ReturnEmptyList_When_UserHasNotExpensesInThisWeek() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllWeekExpenses() when user has 3 expenses in this week")
    void Should_Return2ElementsList_When_UserHasExpensesInThisWeek() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllMonthExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_monthQuery() {

        //given
        String username = "user_with_zero_expenses";
        int monthOrdinal = 12;

        //when
        int actualNumberOfUserExpenses = expenseQueryRepository.findAllMonthExpenses(username,monthOrdinal).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllMonthExpenses() when user hasn't any expense in this month")
    void  Should_ReturnEmptyList_When_UserHasNotExpensesWithThisMonth() {

        //given
        String username = "john_doe";
        int monthOrdinal = 12;

        //when
        int actualNumberOfAllUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();
        int actualNumberOfUserExpensesInThisMonth = expenseQueryRepository.findAllMonthExpenses(username,monthOrdinal).size();
        int expectedNumberOfUserExpensesInThisMonth = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisMonth,actualNumberOfUserExpensesInThisMonth);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllMonthExpenses() when user has 3 expenses in this month")
    void  Should_Return3ElementsList_When_UserHas3ExpensesWithThisMonth() {

        //given
        String username = "john_doe";
        int monthOrdinal = 7;

        //when
        int actualNumberOfUserExpensesInThisMonth = expenseQueryRepository.findAllMonthExpenses(username,monthOrdinal).size();
        int expectedNumberOfUserExpensesInThisMonth = 3;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisMonth,actualNumberOfUserExpensesInThisMonth);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllYearExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_yearQuery() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllYearExpenses() when user hasn't any expense in this year")
    void  Should_ReturnEmptyList_When_UserHasNotExpensesWithThisYear() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAlYearExpenses() when user has 1 expenses in this year")
    void  Should_Return1ElementsList_When_UserHas1ExpensesWithThisYear() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllPeriodExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_PeriodQuery() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllPeriodExpenses() when user hasn't any expense in this period")
    void  Should_ReturnEmptyList_When_UserHasNotExpensesInThisPeriod() {

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
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllPeriodExpenses() when user has 1 expense in this period")
    void  Should_Return1ElementsList_When_UserHas1ExpensesWithThisPeriod() {

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




}