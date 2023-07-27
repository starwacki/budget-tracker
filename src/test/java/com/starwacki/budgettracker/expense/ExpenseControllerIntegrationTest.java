package com.starwacki.budgettracker.expense;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExpenseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseQueryRepository expenseQueryRepository;


    private static final String ENDPOINT_REQUEST_MAPPING = "/expense";

    @Test
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllUsernameExpenses()  when user has 4 expenses")
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
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllUsernameExpenses() when user hasn't expenses")
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
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() when user has expenses in category")
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
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() when user hasn't any expenses")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotAnyExpenses_categoryQuery() throws Exception {

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
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllUsernameExpensesWithThisExpenseCategory() when user hasn't expenses in category")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotExpensesInCategory() throws Exception {

        //given
        String username = "john_doe";
        ExpenseCategory expenseCategory = ExpenseCategory.ANIMALS;

        //when
        int expectedExpensesSize = 0;

        //then
        //(check that user has any expenses)
        assertNotEquals(0, expenseQueryRepository.findAllUsernameExpenses(username).size());
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/category="+expenseCategory))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllDayExpenses() when user has expenses with this date")
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
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllDayExpenses() when user hasn't any expenses")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotAnyExpenses_dateQuery() throws Exception {

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
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllDayExpenses() when user hasn't expenses with this date")
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

    @ParameterizedTest
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @ValueSource(strings = {"2023-07-24", "2023-07-25", "2023-07-26","2023-07-27","2023-07-28","2023-07-29","2023-07-30"})
    @DisplayName("Test findAllWeekExpenses() when user has expenses in this week")
    void Should_Return200StatusCodeAnd3ExpenseList_When_UserHas3ExpensesInThisWeek(String dateString) throws Exception {

        //given
        String username = "pet_owner";
        LocalDate date = LocalDate.parse(dateString);

        //when
        int expectedExpensesSize = 3;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/week="+date))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllWeekExpenses() when user hasn't any expenses")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotAnyExpenses_weekQuery() throws Exception {

        //given
        String username = "user_without_any_expenses";
        LocalDate date = LocalDate.of(2023,10,12);

        //when
        int expectedExpensesSize = 0;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/week="+date))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllWeekExpenses() when user hasn't expenses in this week")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotExpensesInThisWeek() throws Exception {

        ///given
        String username = "pet_owner";
        LocalDate date = LocalDate.of(2013,11,10);

        //when
        int expectedExpensesSize = 0;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/week="+date))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllMonthExpenses() when user has expense with this month")
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
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllMonthExpenses() when user hasn't any expenses")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotAnyExpenses_monthQuery() throws Exception {

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
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllMonthExpenses() when user hasn't expenses with this month")
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
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
        int beforeAddUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();

        //then
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/"+username).content(objectMapper.writeValueAsString(expenseDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int actualUserExpenses = expenseQueryRepository.findAllUsernameExpenses(username).size();
                    assertNotEquals(beforeAddUserExpenses,actualUserExpenses);
                })
                .andExpect(result -> assertEquals(result.getResponse().getStatus(), HttpStatus.CREATED.value()));
    }

    @ParameterizedTest
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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

    @Test
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllYearExpenses() when user has expense with this year")
    void Should_Return200StatusCodeAnd1ExpenseList_When_UserHas1ExpenseWithYear() throws Exception {

        //given
        String username = "alice_wonder";
        int year = 2021;

        //when
        int expectedExpensesSize = 1;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/year="+year))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllYearExpenses() when user hasn't any expenses")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotAnyExpenses_yearQuery() throws Exception {

        //given
        String username = "user_without_any_expenses";
        int year = 2021;

        //when
        int expectedExpensesSize = 0;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/year="+year))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllYearExpenses() when user hasn't expenses with this year")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotExpensesWithYear() throws Exception {

        ///given
        String username = "alice_wonder";
        int year = 2020;

        //when
        int expectedExpensesSize = 0;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/year="+year))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),200))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllPeriodExpenses() when user has expense in this period")
    void Should_Return200StatusCodeAnd1ExpenseList_When_UserHas1ExpenseWithPeriod() throws Exception {

        //given
        String username = "alice_wonder";
        LocalDate fromDate = LocalDate.of(2023,7,12);
        LocalDate toDate = LocalDate.of(2024,10,19);

        //when
        int expectedExpensesSize = 1;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/from="+fromDate+"&to="+toDate))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllPeriodExpenses() when user hasn't any expenses")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotAnyExpenses_periodQuery() throws Exception {

        //given
        String username = "user_without_any_expenses";
        LocalDate fromDate = LocalDate.of(2023,7,12);
        LocalDate toDate = LocalDate.of(2024,10,19);

        //when
        int expectedExpensesSize = 0;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/from="+fromDate+"&to="+toDate))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }

    @Test
    @Sql("classpath:data.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test findAllPeriodExpenses() when user hasn't expenses with this period")
    void Should_Return200StatusCodeAnd0ExpensesList_When_UserHasNotExpensesWithPeriod() throws Exception {

        //given
        String username = "alice_wonder";
        LocalDate fromDate = LocalDate.of(2023,7,20);
        LocalDate toDate = LocalDate.of(2024,10,19);

        //when
        int expectedExpensesSize = 0;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/"+username+"/from="+fromDate+"&to="+toDate))
                .andExpect(result -> assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value()))
                .andExpect(result -> {
                    TypeReference<List<ExpenseDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseDTO> expenses = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
                    assertEquals(expectedExpensesSize,expenses.size());
                });
    }


}
