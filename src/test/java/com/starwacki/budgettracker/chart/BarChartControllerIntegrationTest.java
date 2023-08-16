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
class BarChartControllerIntegrationTest {

    @Autowired
    private BarChartController barChartController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChartExpenseQueryRepository chartExpenseQueryRepository;

    private static final String ENDPOINT_REQUEST_MAPPING = "/barchart";


    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getDailyBarChart()  when user has 0 expenses in database return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndDailyChartWithEmptyHashMap_WhenUserNoExist() throws Exception {

        //given
        String username = "user_without_any_expense";
        LocalDate dateOfWeek = LocalDate.of(2023,10,12);

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/week-chart/"+dateOfWeek))
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
    @DisplayName("Test getDailyBarChart()  when user hasn't any expenses in this week return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndEmptyDailyChart_WhenUserExist() throws Exception {

        //given
        String username = "john_doe";
        LocalDate dateOfWeek = LocalDate.of(2023,10,12);

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/week-chart/"+dateOfWeek))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<DayOfWeek>> typeReference  = new TypeReference<>() {};
                    ChartDTO<DayOfWeek> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                    assertThat(chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size(),is(not(0)));
                });
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getDailyBarChart()  when user has expenses in this week return 200 HTTP Status code and chart")
    void should_Return200StatusAndDailyChart_WhenUserExist() throws Exception {

        //given
        String username = "john_doe";
        LocalDate dateOfWeek = LocalDate.of(2023,7,15);

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/week-chart/"+dateOfWeek))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<DayOfWeek>> typeReference  = new TypeReference<>() {};
                    ChartDTO<DayOfWeek> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    int expectedChartSize = 2;
                    assertThat(chart.expenses().size(),is(expectedChartSize));

                    double expectedMoneyAmountOfFirstDate = 75.99+50.0;
                    assertThat(chart.expenses().get(LocalDate.of(2023,7,15).getDayOfWeek()).getMoneyAmount(),is(expectedMoneyAmountOfFirstDate));

                    double expectedMoneyAmountOfSecondDate = 120;
                    assertThat(chart.expenses().get(LocalDate.of(2023,7,13).getDayOfWeek()).getMoneyAmount(),is(expectedMoneyAmountOfSecondDate));
                });
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getMonthlyBarChart()  when user has 0 expenses in database return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndMonthlyChartWithEmptyHashMap_WhenUserNoExist() throws Exception {

        //given
        String username = "user_without_any_expense";
        int year = 2023;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/year-chart/"+year))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<Month>> typeReference  = new TypeReference<>() {};
                    ChartDTO<Month> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                });

    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getMonthlyBarChart()  when user hasn't any expenses in this year return 200 HTTP Status code and empty chart")
    void should_Return200StatusAndEmptyChart_WhenUserExist() throws Exception {

        //given
        String username = "john_doe";
        int year = 2020;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/year-chart/"+year))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<Month>> typeReference  = new TypeReference<>() {};
                    ChartDTO<Month> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(chart.expenses().size(),is(0));
                    assertThat(chartExpenseQueryRepository.findAllUsernameChartExpenses(username).size(),is(not(0)));
                });
    }

    @Test
    @Sql("classpath:insert_expenses.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Test getMonthlyBarChart()  when user has expenses in this year return 200 HTTP Status code and chart")
    void should_Return200StatusAndChart_WhenUserExist() throws Exception {

        //given
        String username = "john_doe";
        int year = 2023;

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username+"/year-chart/"+year))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<ChartDTO<Month>> typeReference  = new TypeReference<>() {};
                    ChartDTO<Month> chart = objectMapper.readValue(result.getResponse().getContentAsString(),typeReference);

                    int expectedChartSize = 2;
                    assertThat(chart.expenses().size(),is(expectedChartSize));

                    double expectedMoneyAmountOfFirstMonth = 75.99+50.0+120;
                    assertThat(chart.expenses().get(Month.JULY).getMoneyAmount(),is(expectedMoneyAmountOfFirstMonth));

                    double expectedMoneyAmountOfSecondMonth = 10.00;
                    assertThat(chart.expenses().get(Month.APRIL).getMoneyAmount(),is(expectedMoneyAmountOfSecondMonth));
                });
    }



}
