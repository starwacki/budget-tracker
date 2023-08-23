package com.starwacki.budgettracker.chart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

@RestController
@Tag(name = "bar-chart-operations")
interface BarChartOperations {

    @Operation(
            description =
                    "This operation return bar chart of user expenses by given week . If username doesn't exist in database or " +
                            "user doesn't have any expenses in this week return empty bar chart. In this bar chart, chart segments are days of the week, " +
                            "like: MONDAY, TUESDAY, WEDNESDAY... ",
            summary =
                    "Get user week expenses bar chart",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "weekDate",description = "It's any date in week. For example: we have one week: start in 2023-08-21 and" +
                            " end in 2023-08-27, so we can give any date from this period and we will get bar chart of this week expenses",
                            example = "2023-08-17",in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bar chart fetched successively.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                            "{\n" +
                                                    "  \"expenses\": {\n" +
                                                    "    \"WEDNESDAY\": {\n" +
                                                    "      \"moneyAmount\": 450,\n" +
                                                    "      \"percentageAmount\": \"100%\"\n" +
                                                    "    }\n" +
                                                    "  }\n" +
                                                    "}")})),
                    @ApiResponse(responseCode = "400",description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                            "{\n" +
                                                    "  \"timestamp\": \"2023-08-22T12:09:33.404+00:00\",\n" +
                                                    "  \"status\": 400,\n" +
                                                    "  \"error\": \"Bad Request\",\n" +
                                                    "  \"path\": \"/barchart/v1/Username1/week-chart/20231-13-25\"\n" +
                                                    "}"
                                    )}))            }
    )
    @GetMapping(value = "/{username}/week-chart/{weekDate}")
    ResponseEntity<ChartDTO<DayOfWeek>> getWeekBarChart(@PathVariable String username, @PathVariable LocalDate weekDate);

    @Operation(
            description =
                    "This operation return bar chart of user expenses by given year . If username doesn't exist in database or " +
                            "user doesn't have any expenses in this year return empty bar chart. In this bar chart, chart segments are months of the year, " +
                            "like: JANUARY, FEBRUARY, MARCH... ",
            summary =
                    "Get user week expenses bar chart",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "year",description = "Year - value between 2000 and 2050",example = "2023",in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bar chart fetched successively.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                           "{\n" +
                                                   "  \"expenses\": {\n" +
                                                   "    \"AUGUST\": {\n" +
                                                   "      \"moneyAmount\": 450,\n" +
                                                   "      \"percentageAmount\": \"60%\"\n" +
                                                   "    },\n" +
                                                   "    \"JULY\": {\n" +
                                                   "      \"moneyAmount\": 300,\n" +
                                                   "      \"percentageAmount\": \"40%\"\n" +
                                                   "    }\n" +
                                                   "  }\n" +
                                                   "}")})),
                    @ApiResponse(responseCode = "400",description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                            "{\n" +
                                                    "  \"timestamp\": \"2023-08-22T12:09:33.404+00:00\",\n" +
                                                    "  \"status\": 400,\n" +
                                                    "  \"error\": \"Bad Request\",\n" +
                                                    "  \"path\": \"/barchart/v1/Username1/year-chart/202313\"\n" +
                                                    "}"
                                    )}))            }
    )
    @GetMapping(value = "/{username}/year-chart/{year}")
    ResponseEntity<ChartDTO<Month>> getYearBarChart(@PathVariable String username, @PathVariable @Min(value = 2000) @Max(value = 2050) int year);
}
