package com.starwacki.budgettracker.chart;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class PieChartControllerIntegrationTest {

    @Autowired
    private PieChartController pieChartController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChartExpenseQueryRepository chartExpenseQueryRepository;

    private static final String ENDPOINT_REQUEST_MAPPING = "/piechart";


    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUsernameCategoriesExpenses()  when user has 0 expenses in database return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndChartWithEmptyHashMap_WhenUserNoExist() throws Exception {

        //given
        String username = "user_without_any_expense";

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<DayOfWeek>> typeReference  = new TypeReference<>() {};
                    ChartDTO<DayOfWeek> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                });

    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUsernameCategoriesExpenses()  when user has expenses in this week return 200 HTTP Status code and chart")
    void should_Return200StatusAndChart_WhenUserExist() throws Exception {

        //given
        String username = "john_doe";

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    int expectedChartSize = 3;
                    assertThat(chart.expenses().size(),is(expectedChartSize));

                    double expectedMoneyAmountOfFirstCategory = 75.99+10.0;
                    assertThat(chart.expenses().get("FOOD").getMoneyAmount(),is(expectedMoneyAmountOfFirstCategory));

                    double expectedMoneyAmountOfSecondCategory = 50;
                    assertThat(chart.expenses().get("CAR").getMoneyAmount(),is(expectedMoneyAmountOfSecondCategory));

                    double expectedMoneyAmountOfThirdCategory = 120;
                    assertThat(chart.expenses().get("HOME").getMoneyAmount(),is(expectedMoneyAmountOfThirdCategory));
                });
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserWeekCategoriesExpenses()  when user has 0 expenses in database return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndChartWithEmptyHashMap_WhenUserNoExist_WeekExpenses() throws Exception {

        //given
        String username = "user_without_any_expense";
        LocalDate dateOfWeek = LocalDate.of(2023,10,12);

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/week-chart/"+dateOfWeek))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                });

    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserWeekCategoriesExpenses()  when user hasn't any expenses in this week return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndEmptyChart_WhenUserExist_WeekExpenses() throws Exception {

        //given
        String username = "john_doe";
        LocalDate dateOfWeek = LocalDate.of(2023,10,12);

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/week-chart/"+dateOfWeek))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                    assertThat(chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size(),is(not(0)));
                });
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserWeekCategoriesExpenses()  when user has expenses in this week return 200 HTTP Status code and chart")
    void should_Return200StatusAndChart_WhenUserExist_WeekExpenses() throws Exception {

        //given
        String username = "john_doe";
        LocalDate dateOfWeek = LocalDate.of(2023,7,15);

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/week-chart/"+dateOfWeek))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    int expectedChartSize = 3;
                    assertThat(chart.expenses().size(),is(expectedChartSize));

                    double expectedMoneyAmountOfFirstCategory = 75.99;
                    assertThat(chart.expenses().get("FOOD").getMoneyAmount(),is(expectedMoneyAmountOfFirstCategory));

                    double expectedMoneyAmountOfSecondCategory = 50;
                    assertThat(chart.expenses().get("CAR").getMoneyAmount(),is(expectedMoneyAmountOfSecondCategory));

                    double expectedMoneyAmountOfThirdCategory = 120;
                    assertThat(chart.expenses().get("HOME").getMoneyAmount(),is(expectedMoneyAmountOfThirdCategory));
                });
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserMonthCategoriesExpenses()  when user has 0 expenses in database return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndChartWithEmptyHashMap_WhenUserNoExist_MonthExpenses() throws Exception {

        //given
        String username = "user_without_any_expense";
        Month month= Month.DECEMBER;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/month-chart/"+month.getValue()))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                });

    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserMonthCategoriesExpenses()  when user hasn't any expenses in this month return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndEmptyChart_WhenUserExist_MonthExpenses() throws Exception {

        //given
        String username = "john_doe";
        Month month= Month.DECEMBER;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/month-chart/"+month.getValue()))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                    assertThat(chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size(),is(not(0)));
                });
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserMonthCategoriesExpenses()  when user has expenses in this month return 200 HTTP Status code and chart")
    void should_Return200StatusAndChart_WhenUserExist_MonthExpenses() throws Exception {

        //given
        String username = "john_doe";
        Month month= Month.JULY;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/month-chart/"+month.getValue()))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    int expectedChartSize = 3;
                    assertThat(chart.expenses().size(),is(expectedChartSize));

                    double expectedMoneyAmountOfFirstCategory = 75.99;
                    assertThat(chart.expenses().get("FOOD").getMoneyAmount(),is(expectedMoneyAmountOfFirstCategory));

                    double expectedMoneyAmountOfSecondCategory = 50;
                    assertThat(chart.expenses().get("CAR").getMoneyAmount(),is(expectedMoneyAmountOfSecondCategory));

                    double expectedMoneyAmountOfThirdCategory = 120;
                    assertThat(chart.expenses().get("HOME").getMoneyAmount(),is(expectedMoneyAmountOfThirdCategory));
                });
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserYearCategoriesExpenses()  when user has 0 expenses in database return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndChartWithEmptyHashMap_WhenUserNoExist_YearExpenses() throws Exception {

        //given
        String username = "user_without_any_expense";
        int year = 2020;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/year-chart/"+year))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                });

    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserYearCategoriesExpenses()  when user hasn't any expenses in year return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndEmptyChart_WhenUserExist_YearExpenses() throws Exception {

        //given
        String username = "john_doe";
        int year = 2020;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/year-chart/"+year))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                    assertThat(chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size(),is(not(0)));
                });
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserYearCategoriesExpenses()  when user has expenses in this year return 200 HTTP Status code and chart")
    void should_Return200StatusAndChart_WhenUserExist_YearExpenses() throws Exception {

        //given
        String username = "john_doe";
        int year = 2023;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/year-chart/"+year))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    int expectedChartSize = 3;
                    assertThat(chart.expenses().size(),is(expectedChartSize));

                    double expectedMoneyAmountOfFirstCategory = 75.99+10;
                    assertThat(chart.expenses().get("FOOD").getMoneyAmount(),is(expectedMoneyAmountOfFirstCategory));

                    double expectedMoneyAmountOfSecondCategory = 50;
                    assertThat(chart.expenses().get("CAR").getMoneyAmount(),is(expectedMoneyAmountOfSecondCategory));

                    double expectedMoneyAmountOfThirdCategory = 120;
                    assertThat(chart.expenses().get("HOME").getMoneyAmount(),is(expectedMoneyAmountOfThirdCategory));
                });
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserPeriodCategoriesExpenses()  when user has 0 expenses in database return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndChartWithEmptyHashMap_WhenUserNoExist_PeriodExpenses() throws Exception {

        //given
        String username = "user_without_any_expense";
        LocalDate startDate = LocalDate.of(2023,10,12);
        LocalDate endDate = LocalDate.of(2023,10,12);

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/period-chart?from="+startDate+"&to="+endDate))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                });

    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserPeriodCategoriesExpenses()  when user hasn't any expenses in this period return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndEmptyChart_WhenUserExist_PeriodExpenses() throws Exception {

        //given
        String username = "john_doe";
        LocalDate startDate = LocalDate.of(2023,9,12);
        LocalDate endDate = LocalDate.of(2023,10,12);

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/period-chart?from="+startDate+"&to="+endDate))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                    assertThat(chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size(),is(not(0)));
                });
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getPieChartOfUserPeriodCategoriesExpenses() when user has expenses in this period return 200 HTTP Status code and chart")
    void should_Return200StatusAndChart_WhenUserExist_PeriodExpenses() throws Exception {

        //given
        String username = "john_doe";
        LocalDate startDate = LocalDate.of(2023,6,12);
        LocalDate endDate = LocalDate.of(2023,10,12);

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/period-chart?from="+startDate+"&to="+endDate))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<String>> typeReference  = new TypeReference<>() {};
                    ChartDTO<String> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    int expectedChartSize = 3;
                    assertThat(chart.expenses().size(),is(expectedChartSize));

                    double expectedMoneyAmountOfFirstCategory = 75.99;
                    assertThat(chart.expenses().get("FOOD").getMoneyAmount(),is(expectedMoneyAmountOfFirstCategory));

                    double expectedMoneyAmountOfSecondCategory = 50;
                    assertThat(chart.expenses().get("CAR").getMoneyAmount(),is(expectedMoneyAmountOfSecondCategory));

                    double expectedMoneyAmountOfThirdCategory = 120;
                    assertThat(chart.expenses().get("HOME").getMoneyAmount(),is(expectedMoneyAmountOfThirdCategory));
                });
    }

}
