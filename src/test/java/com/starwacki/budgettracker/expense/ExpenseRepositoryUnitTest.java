package com.starwacki.budgettracker.expense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//Use only layer without starting server to test JPQL queries.
@DataJpaTest
@ActiveProfiles("test")
public class ExpenseRepositoryUnitTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        expenseRepository.deleteAll();

        Expense expense1 = Expense.builder()
                .name("Groceries")
                .description("Weekly groceries shopping")
                .username("john_doe")
                .expenseCategory(ExpenseCategory.FOOD)
                .date(LocalDate.of(2023, 7, 15))
                .time(LocalTime.of(12, 30))
                .moneyValue(75.99)
                .build();

        Expense expense2 = Expense.builder()
                .name("Movie Tickets")
                .description("Cinema tickets for a movie night")
                .username("alice_wonder")
                .expenseCategory(ExpenseCategory.ENTERTAINMENT)
                .date(LocalDate.of(2023, 7, 18))
                .time(LocalTime.of(20, 15))
                .moneyValue(32.50)
                .build();

        Expense expense3 = Expense.builder()
                .name("Gasoline")
                .description("Fuel for the car")
                .username("john_doe")
                .expenseCategory(ExpenseCategory.CAR)
                .date(LocalDate.of(2023, 7, 15))
                .time(LocalTime.of(15, 45))
                .moneyValue(50.00)
                .build();

        Expense expense4 = Expense.builder()
                .name("Home Decor")
                .description("Purchased new curtains and cushions")
                .username("john_doe")
                .expenseCategory(ExpenseCategory.HOME)
                .date(LocalDate.of(2023, 7, 13))
                .time(LocalTime.of(14, 0))
                .moneyValue(120.00)
                .build();

        Expense expense5 = Expense.builder()
                .name("Pet Food")
                .description("Bought pet food for the month")
                .username("pet_owner")
                .expenseCategory(ExpenseCategory.ANIMALS)
                .date(LocalDate.of(2023, 7, 24))
                .time(LocalTime.of(18, 20))
                .moneyValue(25.75)
                .build();

        Expense expense6 = Expense.builder()
                .name("Gardening Supplies")
                .description("Purchased seeds and gardening tools")
                .username("pet_owner")
                .expenseCategory(ExpenseCategory.HOBBY)
                .date(LocalDate.of(2023, 7, 30))
                .time(LocalTime.of(11, 45))
                .moneyValue(40.00)
                .build();

        Expense expense7 = Expense.builder()
                .name("Books")
                .description("Bought some new books to read")
                .username("pet_owner")
                .expenseCategory(ExpenseCategory.EDUCATION)
                .date(LocalDate.of(2023, 7, 28))
                .time(LocalTime.of(16, 30))
                .moneyValue(60.50)
                .build();

        Expense expense8 = Expense.builder()
                .name("Kebab")
                .description("")
                .username("john_doe")
                .expenseCategory(ExpenseCategory.FOOD)
                .date(LocalDate.of(2023, 4, 19))
                .time(LocalTime.of(14, 12))
                .moneyValue(10.00)
                .build();

        expenseRepository.save(expense1);
        expenseRepository.save(expense2);
        expenseRepository.save(expense3);
        expenseRepository.save(expense4);
        expenseRepository.save(expense5);
        expenseRepository.save(expense6);
        expenseRepository.save(expense7);
        expenseRepository.save(expense8);
    }

    @Test
    @DisplayName("Test findAllUsernameExpenses() when user doesn't be in database")
    void Should_ReturnEmptyList_usernameQuery() {

        //given
        String username = "user_with_zero_expenses";

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllUsernameExpenses(username).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test findAllUsernameExpenses() when user has 4 expenses in database")
    void Should_ReturnListWith4Elements() {

        //given
        String username = "john_doe";

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllUsernameExpenses(username).size();
        int expectedNumberOfUserExpenses = 4;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test findAllUsernameExpenses() correct sorting by the newest")
    void Should_ReturnListSortedByDate() {

        //given
        String username = "john_doe";

        //when
        List<Expense> userExpenses = expenseRepository.findAllUsernameExpenses(username);

        int expectedNumberOfUserExpenses = 4;
        int actualNumberOfUserExpenses = userExpenses.size();

        LocalDate expectedDateOfFirstElement = LocalDate.of(2023, 7, 15);
        LocalTime expectedTimeOfFistElement = LocalTime.of(15,45);

        LocalDate actualDateOfFirstElement = userExpenses.get(0).getDate();
        LocalTime actualTimeOfFistElement = userExpenses.get(0).getTime();

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
        assertEquals(expectedDateOfFirstElement,actualDateOfFirstElement);
        assertEquals(expectedTimeOfFistElement,actualTimeOfFistElement);
    }

    @Test
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() when user hasn't expense with this category")
    void Should_ReturnEmptyList_When_UserHasNotExpenseWithThisCategory() {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.OTHER;

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() when user has 2 expenses in this category")
    void Should_Return2ElementsList_When_UserHasExpenseWithThisCategory() {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.FOOD;

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory).size();
        int expectedNumberOfUserExpenses = 2;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() correct sorting")
    void Should_ReturnSortedByDateList_When_UserHasExpenseWithThisCategory() {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.FOOD;

        //when
        List<Expense> userExpenses = expenseRepository.findAllUsernameExpensesWithThisExpenseCategory(username,expenseCategory);

        int expectedNumberOfUserExpenses = 2;
        int actualNumberOfUserExpenses = userExpenses.size();

        LocalDate expectedDateOfFirstElement = LocalDate.of(2023, 7, 15);
        LocalTime expectedTimeOfFistElement = LocalTime.of(12,30);

        LocalDate actualDateOfFirstElement = userExpenses.get(0).getDate();
        LocalTime actualTimeOfFistElement = userExpenses.get(0).getTime();

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
        assertEquals(expectedDateOfFirstElement,actualDateOfFirstElement);
        assertEquals(expectedTimeOfFistElement,actualTimeOfFistElement);
    }

    @Test
    @DisplayName("Test findAllDayExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_dateQuery() {

        //given
        String username = "user_with_zero_expenses";
        LocalDate date = LocalDate.of(2022,10,12);

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllDayExpenses(username,date).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test findAllDayExpenses() when user hasn't any expense with this date")
    void Should_ReturnEmptyList_When_UserHasNotExpensesWithThisDate() {

        //given
        String username = "john_doe";
        LocalDate dateWithoutAnyExpense = LocalDate.of(2022,10,12);

        //when
        int actualNumberOfAllUserExpenses = expenseRepository.findAllUsernameExpenses(username).size();
        int actualNumberOfUserExpensesInThisDate =expenseRepository.findAllDayExpenses(username,dateWithoutAnyExpense).size();
        int expectedNumberOfUserExpensesInThisDate = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisDate,actualNumberOfUserExpensesInThisDate);
    }


    @Test
    @DisplayName("Test findAllDayExpenses() when user has 2 expenses with this date")
    void Should_Return2ElementsList_When_UserHasExpensesWithThisDate() {

        //given
        String username = "john_doe";
        LocalDate dateWithoutAnyExpense = LocalDate.of(2023,7,15);

        //when
        int actualNumberOfUserExpensesInThisDate =expenseRepository.findAllDayExpenses(username,dateWithoutAnyExpense).size();
        int expectedNumberOfUserExpensesInThisDate = 2;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisDate,actualNumberOfUserExpensesInThisDate);
    }

    @Test
    @DisplayName("Test findAllWeekExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_weekQuery() {

        //given
        String username = "user_with_zero_expenses";
        LocalDate mondayDate = LocalDate.of(2023,7,24);
        LocalDate sundayDate = LocalDate.of(2023,7,30);

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllWeekExpenses(username,mondayDate,sundayDate).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test findAllWeekExpenses() when user hasn't any expense in this week")
    void Should_ReturnEmptyList_When_UserHasNotExpensesInThisWeek() {

        //given
        String username = "john_doe";
        LocalDate mondayDate = LocalDate.of(2023,7,24);
        LocalDate sundayDate = LocalDate.of(2023,7,30);

        //when
        int actualNumberOfAllUserExpenses = expenseRepository.findAllUsernameExpenses(username).size();
        int actualNumberOfUserExpensesInThisWeek =expenseRepository.findAllWeekExpenses(username,mondayDate,sundayDate).size();
        int expectedNumberOfUserExpensesInThisWeek = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisWeek,actualNumberOfUserExpensesInThisWeek);
    }


    @Test
    @DisplayName("Test findAllWeekExpenses() when user has 3 expenses in this week")
    void Should_Return2ElementsList_When_UserHasExpensesInThisWeek() {

        //given
        String username = "pet_owner";
        LocalDate mondayDate = LocalDate.of(2023,7,24);
        LocalDate sundayDate = LocalDate.of(2023,7,30);

        //when
        int actualNumberOfUserExpensesInThisWeek =expenseRepository.findAllWeekExpenses(username,mondayDate,sundayDate).size();
        int expectedNumberOfUserExpensesInThisWeek = 3;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisWeek,actualNumberOfUserExpensesInThisWeek);
    }

    @Test
    @DisplayName("Test findAllMonthExpenses() when user hasn't any expense in database")
    void Should_ReturnEmptyList_monthQuery() {

        //given
        String username = "user_with_zero_expenses";
        int monthOrdinal = 12;

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllMonthExpenses(username,monthOrdinal).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test findAllMonthExpenses() when user hasn't any expense in this month")
    void  Should_ReturnEmptyList_When_UserHasNotExpensesWithThisMonth() {

        //given
        String username = "john_doe";
        int monthOrdinal = 12;

        //when
        int actualNumberOfAllUserExpenses = expenseRepository.findAllUsernameExpenses(username).size();
        int actualNumberOfUserExpensesInThisMonth = expenseRepository.findAllMonthExpenses(username,monthOrdinal).size();
        int expectedNumberOfUserExpensesInThisMonth = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisMonth,actualNumberOfUserExpensesInThisMonth);
    }

    @Test
    @DisplayName("Test findAllMonthExpenses() when user has 3 expenses in this month")
    void  Should_Return3ElementsList_When_UserHas3ExpensesWithThisMonth() {

        //given
        String username = "john_doe";
        int monthOrdinal = 7;

        //when
        int actualNumberOfUserExpensesInThisMonth = expenseRepository.findAllMonthExpenses(username,monthOrdinal).size();
        int expectedNumberOfUserExpensesInThisMonth = 3;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisMonth,actualNumberOfUserExpensesInThisMonth);
    }









}
