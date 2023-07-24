package com.starwacki.budgettracker.expense;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExpenseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExpenseRepository expenseRepository;

    private static final String ENDPOINT_REQUEST_MAPPING = "/expense";

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
    @DisplayName("Test findAllExpensesByUsername()  when user has 4 expenses")
    void Should_Return200StatusCodeAnd4ExpensesList_When_UserHas4Expenses() throws Exception {

        //given
        String username = "john_doe";

        //when
        int expectedExpensesSize = 4;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @DisplayName("Test findAllExpensesByUsername() when user hasn't expenses")
    void Should_Return200StatusCodeAnd4ExpensesList_When_UserHasNoExpenses() throws Exception {

        //given
        String username = "user_without_expenses";

        //when
        int expectedExpensesSize = 0;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @DisplayName("Test findAllExpensesByUsernameAndCategory() when user has expenses in category")
    void Should_Return200StatusCodeAnd2ExpensesList_When_UserHas2ExpensesInCategory() throws Exception {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.FOOD;

        //when
        int expectedExpensesSize = 2;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/category="+expenseCategory))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @DisplayName("Test findAllExpensesByUsernameAndCategory() when user hasn't any expenses")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotAnyExpenses_category() throws Exception {

        //given
        String username = "user_without_any_expenses";
        ExpenseCategory expenseCategory = ExpenseCategory.FOOD;

        //when
        int expectedExpensesSize = 0;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/category="+expenseCategory))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @DisplayName("Test findAllExpensesByUsernameAndCategory() when user hasn't expenses in category")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotExpensesInCategory() throws Exception {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.ANIMALS;

        //when
        int expectedExpensesSize = 0;

        //then
        //(check that user has any expenses)
        assertNotEquals(0,expenseRepository.findAllByUsername(username).size());
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/category="+expenseCategory))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @DisplayName("Test  findAllExpensesByUsernameAndDate() when user has expenses with this date")
    void Should_Return200StatusCodeAnd2ExpensesList_When_UserHas2ExpensesWithDate() throws Exception {

        //given
        String username = "john_doe";
        LocalDate date = LocalDate.of(2023,7,15);

        //when
        int expectedExpensesSize = 2;


        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/date="+date))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @DisplayName("Test  findAllExpensesByUsernameAndDate() when user hasn't any expenses")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotAnyExpenses_date() throws Exception {

        //given
        String username = "user_without_any_expenses";
        LocalDate date = LocalDate.of(2015,11,10);

        //when
        int expectedExpensesSize = 0;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/date="+date))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @DisplayName("Test findAllExpensesByUsernameAndDate() when user hasn't expenses with this date")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotExpensesWithDate() throws Exception {

        //given
        String username = "john_doe";
        LocalDate date = LocalDate.of(2015,11,10);

        //when
        int expectedExpensesSize = 0;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/date="+date))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @DisplayName("Test  findAllExpensesByUsernameAndMoth() when user has expense with this month")
    void Should_Return200StatusCodeAnd1ExpenseList_When_UserHas1ExpenseWithMonth() throws Exception {

        //given
        String username = "john_doe";
        int monthOrdinal = Month.APRIL.getValue();

        //when
        int expectedExpensesSize = 1;


        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/month="+monthOrdinal))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @DisplayName("Test  findAllExpensesByUsernameAndDate() when user hasn't any expenses")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotAnyExpenses_month() throws Exception {

        //given
        String username = "user_without_any_expenses";
        int monthOrdinal = Month.APRIL.getValue();

        //when
        int expectedExpensesSize = 0;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/month="+monthOrdinal))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @DisplayName("Test findAllExpensesByUsernameAndDate() when user hasn't expenses with this date")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotExpensesWithMonth() throws Exception {

        ///given
        String username = "john_doe";
        int monthOrdinal = Month.FEBRUARY.getValue();

        //when
        int expectedExpensesSize = 0;


        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/month="+monthOrdinal))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),200))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @DisplayName("Test addNewExpenseToUser() add expense")
    void Should_AddExpenseToDatabase_AndReturn204StatusCode() throws Exception {

        //given
        String username = "john_doe";
        ExpenseDTO expenseDTO = ExpenseDTO
                .builder()
                .name("NEW_EXPENSE")
                .description("DESCRIPTION")
                .date(LocalDate.of(2020,10,1))
                .time(LocalTime.of(15,41))
                .expenseCategory(ExpenseCategory.CAR)
                .moneyValue(20.0)
                .build();

        //when
        int beforeAddUserExpenses = expenseRepository.findAllByUsername(username).size();

        //then
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/"+username).content(objectMapper.writeValueAsString(expenseDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int actualUserExpenses = expenseRepository.findAllByUsername(username).size();
                    assertNotEquals(beforeAddUserExpenses,actualUserExpenses);
                })
                .andExpect(result -> assertEquals(result.getResponse().getStatus(), HttpStatus.CREATED.value()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"","  ", "         ", "sh","s","",
    "string with more than 40 characters, -------------------------- "})
    @DisplayName("Test addNewExpenseToUser() throw exception when name is empty or apart from range")
    void Should_ThrowMethodArgumentNotValidException_When_EmptyName(String name) throws Exception {

        //given
        String username = "john_doe";
        ExpenseDTO expenseDTO = ExpenseDTO
                .builder()
                .name(name)
                .description("3443")
                .date(LocalDate.of(2020,10,1))
                .time(LocalTime.of(15,41))
                .expenseCategory(ExpenseCategory.CAR)
                .moneyValue(20.0)
                .build();

        //then
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING + "/" + username)
                        .content(objectMapper.writeValueAsString(expenseDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

    }

    @Test
    @DisplayName("Test addNewExpenseToUser() throw exception when too long description")
    void Should_ThrowMethodArgumentNotValidException_When_TooLongDescription() throws Exception {

        //given
        String username = "john_doe";
        String description="More than 200 characters description.................................." +
                "................................................................................." +
                "................................................................................." +
                "................................................................................." +
                "................................................................................." +
                ".................................................................................";
        ExpenseDTO expenseDTO = ExpenseDTO
                .builder()
                .name("name")
                .description(description)
                .date(LocalDate.of(2020,10,1))
                .time(LocalTime.of(15,41))
                .expenseCategory(ExpenseCategory.CAR)
                .moneyValue(20.0)
                .build();

        //then
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING + "/" + username)
                        .content(objectMapper.writeValueAsString(expenseDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-5.0,-0.11,1000001})
    @DisplayName("Test addNewExpenseToUser() throw exception when money value is out of range")
    void Should_ThrowMethodArgumentNotValidException_When_MoneyValueOutOfRange(double moneyValue) throws Exception {

        //given
        String username = "john_doe";
        ExpenseDTO expenseDTO = ExpenseDTO
                .builder()
                .name("name")
                .description("3443")
                .date(LocalDate.of(2020,10,1))
                .time(LocalTime.of(15,41))
                .expenseCategory(ExpenseCategory.CAR)
                .moneyValue(moneyValue)
                .build();

        //then
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING + "/" + username)
                        .content(objectMapper.writeValueAsString(expenseDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

    }


}
