package com.starwacki.budgettracker.chart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@ActiveProfiles("test")
class ChartExpenseQueryRepositoryUnitTest {

    @Autowired
    private ChartExpenseQueryRepository chartExpenseQueryRepository;

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllUsernameGraphExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_usernameQuery() {

        //given
        String username = "user_with_zero_expenses";

        //when
        int actualNumberOfUserExpenses = chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllUsernameGraphExpenses() when user has 4 expenses in database")
    void Should_ReturnListWith4Elements() {

        //given
        String username = "john_doe";

        //when
        int actualNumberOfUserExpenses = chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size();
        int expectedNumberOfUserExpenses = 4;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllUsernameGraphExpensesWithThisExpenseCategory() when user hasn't any expense in database")
    void Should_ReturnEmptyList_expenseCategoryQuery() {

        //given
        String username = "user_with_zero_expenses";
        String expenseCategory = "OTHER";


        //when
        int actualNumberOfUserExpenses = chartExpenseQueryRepository.findAllUsernameChartExpensesWithThisExpenseCategory(username,expenseCategory).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllUsernameGraphExpensesWithThisExpenseCategory() when user hasn't expense with this category")
    void Should_ReturnEmptyList_When_UserHasNotExpenseWithThisCategory() {

        //given
        String username = "john_doe";
        String expenseCategory = "OTHER";

        //when
        int actualNumberOfUserExpenses = chartExpenseQueryRepository.findAllUsernameChartExpensesWithThisExpenseCategory(username,expenseCategory).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllUsernameGraphExpensesWithThisExpenseCategory() when user has 2 expenses in this category")
    void Should_Return2ElementsList_When_UserHasExpenseWithThisCategory() {

        //given
        String username = "john_doe";
        String expenseCategory = "FOOD";

        //when
        int actualNumberOfUserExpenses = chartExpenseQueryRepository.findAllUsernameChartExpensesWithThisExpenseCategory(username,expenseCategory).size();
        int expectedNumberOfUserExpenses = 2;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllDayGraphExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_dateQuery() {

        //given
        String username = "user_with_zero_expenses";
        LocalDate date = LocalDate.of(2022,10,12);

        //when
        int actualNumberOfUserExpenses = chartExpenseQueryRepository.findAllDayChartExpenses(username,date).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllDayGraphExpenses() when user hasn't any expense with this date")
    void Should_ReturnEmptyList_When_UserHasNotGraphExpensesWithThisDate() {

        //given
        String username = "john_doe";
        LocalDate dateWithoutAnyExpense = LocalDate.of(2022,10,12);

        //when
        int actualNumberOfAllUserExpenses = chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size();
        int actualNumberOfUserExpensesInThisDate =  chartExpenseQueryRepository.findAllDayChartExpenses(username,dateWithoutAnyExpense).size();
        int expectedNumberOfUserExpensesInThisDate = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisDate,actualNumberOfUserExpensesInThisDate);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllDayGraphExpenses() when user has 2 expenses with this date")
    void Should_Return2ElementsList_When_UserHasGraphExpensesWithThisDate() {

        //given
        String username = "john_doe";
        LocalDate date = LocalDate.of(2023,7,15);

        //when
        int actualNumberOfUserExpensesInThisDate = chartExpenseQueryRepository.findAllDayChartExpenses(username,date).size();
        int expectedNumberOfUserExpensesInThisDate = 2;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisDate,actualNumberOfUserExpensesInThisDate);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllWeekGraphExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_weekQuery() {

        //given
        String username = "user_with_zero_expenses";
        LocalDate mondayDate = LocalDate.of(2023,7,24);
        LocalDate sundayDate = LocalDate.of(2023,7,30);

        //when
        int actualNumberOfUserExpenses = chartExpenseQueryRepository.findAllChartGraphExpenses(username,mondayDate,sundayDate).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllWeekGraphExpenses() when user hasn't any expense in this week")
    void Should_ReturnEmptyList_When_UserHasNotGraphExpensesInThisWeek() {

        //given
        String username = "john_doe";
        LocalDate mondayDate = LocalDate.of(2023,7,24);
        LocalDate sundayDate = LocalDate.of(2023,7,30);

        //when
        int actualNumberOfAllUserExpenses = chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size();
        int actualNumberOfUserExpensesInThisWeek = chartExpenseQueryRepository.findAllChartGraphExpenses(username,mondayDate,sundayDate).size();
        int expectedNumberOfUserExpensesInThisWeek = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisWeek,actualNumberOfUserExpensesInThisWeek);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllWeekGraphExpenses() when user has 3 expenses in this week")
    void Should_Return2ElementsList_When_UserHasExpensesInThisWeek() {

        //given
        String username = "pet_owner";
        LocalDate mondayDate = LocalDate.of(2023,7,24);
        LocalDate sundayDate = LocalDate.of(2023,7,30);

        //when
        int actualNumberOfUserExpensesInThisWeek = chartExpenseQueryRepository.findAllChartGraphExpenses(username,mondayDate,sundayDate).size();
        int expectedNumberOfUserExpensesInThisWeek = 3;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisWeek,actualNumberOfUserExpensesInThisWeek);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllMonthGraphExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_monthQuery() {

        //given
        String username = "user_with_zero_expenses";
        int monthOrdinal = 12;

        //when
        int actualNumberOfUserExpenses = chartExpenseQueryRepository.findAllMonthChartExpenses(username,monthOrdinal).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllMonthGraphExpenses() when user hasn't any expense in this month")
    void  Should_ReturnEmptyList_When_UserHasNotGraphExpensesWithThisMonth() {

        //given
        String username = "john_doe";
        int monthOrdinal = 12;

        //when
        int actualNumberOfAllUserExpenses = chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size();
        int actualNumberOfUserExpensesInThisMonth = chartExpenseQueryRepository.findAllMonthChartExpenses(username,monthOrdinal).size();
        int expectedNumberOfUserExpensesInThisMonth = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisMonth,actualNumberOfUserExpensesInThisMonth);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllMonthGraphExpenses() when user has 3 expenses in this month")
    void  Should_Return3ElementsList_When_UserHas3ExpensesWithThisMonth() {

        //given
        String username = "john_doe";
        int monthOrdinal = 7;

        //when
        int actualNumberOfUserExpensesInThisMonth = chartExpenseQueryRepository.findAllMonthChartExpenses(username,monthOrdinal).size();
        int expectedNumberOfUserExpensesInThisMonth = 3;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisMonth,actualNumberOfUserExpensesInThisMonth);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllYearGraphExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_yearQuery() {

        //given
        String username = "user_with_zero_expenses";
        int year = 2023;

        //when
        int actualNumberOfUserExpenses = chartExpenseQueryRepository.findAllYearChartExpenses(username,year).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllYearGraphExpenses() when user hasn't any expense in this year")
    void  Should_ReturnEmptyList_When_UserHasNotGraphExpensesWithThisYear() {

        //given
        String username = "alice_wonder";
        int year = 2020;

        //when
        int actualNumberOfAllUserExpenses = chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size();
        int actualNumberOfUserExpensesInThisYear = chartExpenseQueryRepository.findAllYearChartExpenses(username,year).size();
        int expectedNumberOfUserExpensesInThisYear = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisYear,actualNumberOfUserExpensesInThisYear);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllYearGraphExpenses() when user has 1 expenses in this year")
    void  Should_Return1ElementsList_When_UserHas1GraphExpensesWithThisYear() {

        //given
        String username = "alice_wonder";
        int year = 2021;

        //when
        int actualNumberOfUserExpensesInThisYear = chartExpenseQueryRepository.findAllYearChartExpenses(username,year).size();
        int expectedNumberOfUserExpensesInThisYear = 1;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisYear,actualNumberOfUserExpensesInThisYear);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllPeriodGraphExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_PeriodQuery() {

        //given
        String username = "user_with_zero_expenses";
        LocalDate startDate = LocalDate.of(2023,10,12);
        LocalDate endDate = LocalDate.of(2024,10,19);

        //when
        int actualNumberOfUserExpenses = chartExpenseQueryRepository.findAllPeriodChartExpenses(username,startDate,endDate).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllPeriodGraphExpenses() when user hasn't any expense in this period")
    void  Should_ReturnEmptyList_When_UserHasNotGraphExpensesInThisPeriod() {

        //given
        String username = "alice_wonder";
        LocalDate startDate = LocalDate.of(2023,10,12);
        LocalDate endDate = LocalDate.of(2024,10,19);

        //when
        int actualNumberOfAllUserExpenses = chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size();
        int actualNumberOfUserExpensesInThisPeriod = chartExpenseQueryRepository.findAllPeriodChartExpenses(username,startDate,endDate).size();
        int expectedNumberOfUserExpensesInThisPeriod = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisPeriod,actualNumberOfUserExpensesInThisPeriod);
    }

    @Test
    @Sql("classpath:data.sql")
    @DisplayName("Test findAllPeriodGraphExpenses() when user has 1 expense in this period")
    void  Should_Return1ElementsList_When_UserHas1GraphExpensesWithThisPeriod() {

        //given
        String username = "alice_wonder";
        LocalDate startDate = LocalDate.of(2023,7,12);
        LocalDate endDate = LocalDate.of(2024,10,19);

        //when
        int actualNumberOfUserExpensesInThisPeriod = chartExpenseQueryRepository.findAllPeriodChartExpenses(username,startDate,endDate).size();
        int expectedNumberOfUserExpensesInThisPeriod = 1;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisPeriod,actualNumberOfUserExpensesInThisPeriod);
    }






}
