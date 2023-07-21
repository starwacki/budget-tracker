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

@DataJpaTest
@ActiveProfiles("test")
public class ExpenseRepositoryIntegrationTest {

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
                .date(LocalDate.of(2023, 7, 16))
                .time(LocalTime.of(18, 20))
                .moneyValue(25.75)
                .build();

        Expense expense6 = Expense.builder()
                .name("Gardening Supplies")
                .description("Purchased seeds and gardening tools")
                .username("green_thumb")
                .expenseCategory(ExpenseCategory.HOBBY)
                .date(LocalDate.of(2023, 7, 17))
                .time(LocalTime.of(11, 45))
                .moneyValue(40.00)
                .build();

        Expense expense7 = Expense.builder()
                .name("Books")
                .description("Bought some new books to read")
                .username("bookworm")
                .expenseCategory(ExpenseCategory.EDUCATION)
                .date(LocalDate.of(2023, 7, 21))
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
    @DisplayName("Test findAllByUsername() when user doesn't be in database")
    void findAllByUsername_givenUsername_whenUserHas0Expenses_shouldReturnEmptyList() {

        //given
        String username = "user_with_zero_expenses";

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllByUsername(username).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test findAllByUsername() when user has 4 expenses in database")
    void findAllByUsername_givenUsername_whenUserHas4Expenses_shouldReturnListWith4Elements() {

        //given
        String username = "john_doe";

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllByUsername(username).size();
        int expectedNumberOfUserExpenses = 4;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test findAllByUsername() correct sorting by the newest")
    void findAllByUsername_givenUsername_whenUserHas4Expenses_shouldReturnListWith4ElementsSortedByTheNewest() {

        //given
        String username = "john_doe";

        //when
        List<Expense> userExpenses = expenseRepository.findAllByUsername(username);

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
    @DisplayName("Test findAllByUsernameAndExpenseCategory() when user hasn't expense with this category")
    void findAllByUsernameAndExpenseCategory_givenUsernameAndExpenseCategory_whenUserHas0ExpensesInThisCategory_shouldReturnListWith2Elements() {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.OTHER;

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllByUsernameAndExpenseCategory(username,expenseCategory).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test findAllByUsernameAndExpenseCategory() when user has 2 expenses in this category")
    void findAllByUsernameAndExpenseCategory_givenUsernameAndExpenseCategory_whenUserHas2ExpensesInThisCategory_shouldReturnListWith2Elements() {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.FOOD;

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllByUsernameAndExpenseCategory(username,expenseCategory).size();
        int expectedNumberOfUserExpenses = 2;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test findAllByUsernameAndExpenseCategory() correct sorting")
    void findAllByUsernameAndExpenseCategory_givenUsernameAndExpenseCategory_whenUserHas2ExpensesInThisCategory_shouldReturnListWith2ElementsSortedByTheNewest() {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.FOOD;

        //when
        List<Expense> userExpenses = expenseRepository.findAllByUsernameAndExpenseCategory(username,expenseCategory);

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
    @DisplayName("Test  findAllByUsernameAndDate() when user hasn't any expense in database")
    void findAllByUsernameAndDate_givenUsernameAndDate_whenUserHas0Expenses_shouldReturnEmptyList() {

        //given
        String username = "user_with_zero_expenses";
        LocalDate date = LocalDate.of(2022,10,12);

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllByUsernameAndDate(username,date).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test  findAllByUsernameAndDate() when user hasn't any expense with this date")
    void findAllByUsernameAndDate_givenUsernameAndDate_whenUserHas0ExpensesInThisDate_shouldReturnEmptyList() {

        //given
        String username = "john_doe";
        LocalDate dateWithoutAnyExpense = LocalDate.of(2022,10,12);

        //when
        int actualNumberOfAllUserExpenses = expenseRepository.findAllByUsername(username).size();
        int actualNumberOfUserExpensesInThisDate =expenseRepository.findAllByUsernameAndDate(username,dateWithoutAnyExpense).size();
        int expectedNumberOfUserExpensesInThisDate = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisDate,actualNumberOfUserExpensesInThisDate);
    }

    @Test
    @DisplayName("Test  findAllByUsernameAndDate() when user has has 2 expenses with this date")
    void findAllByUsernameAndDate_givenUsernameAndExistDate_whenUserHasTwoExpensesInThisDate_shouldReturnListWith() {

        //given
        String username = "john_doe";
        LocalDate dateWithoutAnyExpense = LocalDate.of(2023,7,15);

        //when
        int actualNumberOfUserExpensesInThisDate =expenseRepository.findAllByUsernameAndDate(username,dateWithoutAnyExpense).size();
        int expectedNumberOfUserExpensesInThisDate = 2;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisDate,actualNumberOfUserExpensesInThisDate);
    }

    //....
    //
    //


    @Test
    @DisplayName("Test  findAllByUsernameAndMonth() when user hasn't any expense in database")
    void findAllByUsernameAndMonth_givenUsernameAndMonth_whenUserHas0Expenses_shouldReturnEmptyList() {

        //given
        String username = "user_with_zero_expenses";
        int monthOrdinal = 12;

        //when
        int actualNumberOfUserExpenses = expenseRepository.findAllByUsernameAndMonth(username,monthOrdinal).size();
        int expectedNumberOfUserExpenses = 0;

        //then
        assertEquals(expectedNumberOfUserExpenses,actualNumberOfUserExpenses);
    }

    @Test
    @DisplayName("Test  findAllByUsernameAndMonth() when user hasn't any expense in this month")
    void  findAllByUsernameAndMonth_givenUsernameAndMonth_whenUserHas0ExpensesInThisMonth_shouldReturnEmptyList() {

        //given
        String username = "john_doe";
        int monthOrdinal = 12;

        //when
        int actualNumberOfAllUserExpenses = expenseRepository.findAllByUsername(username).size();
        int actualNumberOfUserExpensesInThisMonth = expenseRepository.findAllByUsernameAndMonth(username,monthOrdinal).size();
        int expectedNumberOfUserExpensesInThisMonth = 0;

        //then
        assertNotEquals(0,actualNumberOfAllUserExpenses );
        assertEquals(expectedNumberOfUserExpensesInThisMonth,actualNumberOfUserExpensesInThisMonth);
    }

    @Test
    @DisplayName("Test  findAllByUsernameAndMonth() when user has 3 expenses in this month")
    void findAllByUsernameAndMonth_givenUsernameAndMonth_whenUserHasThreeExpensesInThisMonth_shouldReturnListWith3Elements() {

        //given
        String username = "john_doe";
        int monthOrdinal = 7;

        //when
        int actualNumberOfUserExpensesInThisMonth = expenseRepository.findAllByUsernameAndMonth(username,monthOrdinal).size();
        int expectedNumberOfUserExpensesInThisMonth = 3;

        //then
        assertEquals(expectedNumberOfUserExpensesInThisMonth,actualNumberOfUserExpensesInThisMonth);
    }









}
