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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@Tag(name = "pie-chart-operations")
interface PieChartOperations {

    @Operation(
            description =
                    "This operation return pie chart of all user expenses. If username doesn't exist in database or " +
                    "user doesn't have any expenses return empty pie chart. In this pie chart, chart segments are expense categories, " +
                    "(App expense categories and user own categories) like: FOOD, HOME, CAR... ",
            summary =
                    "Get all user expenses pie chart",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pie chart fetched successively.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                          "{\n" +
                                                  "  \"expenses\": {\n" +
                                                  "    \"FOOD\": {\n" +
                                                  "      \"moneyAmount\": 750,\n" +
                                                  "      \"percentageAmount\": \"100%\"\n" +
                                                  "    }\n" +
                                                  "  }\n" +
                                                  "}")})),
            }
    )
    @GetMapping("/{username}")
    ResponseEntity<ChartDTO<String>> getPieChartOfUsernameCategoriesExpenses(@PathVariable String username);

    @Operation(
            description =
                    "This operation return pie chart of user expenses by given week . If username doesn't exist in database or " +
                    "user doesn't have any expenses in this week return empty pie chart. In this pie chart, chart segments are expense categories, " +
                    "(App expense categories and user own categories) like: FOOD, HOME, CAR... ",
            summary =
                    "Get user week expenses bar chart",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "weekDate",description = "It's any date in week. For example: we have one week: start in 2023-08-21 and" +
                            " end in 2023-08-27, so we can give any date from this period and we will get bar chart of this week expenses",
                            example = "2023-08-17",in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pie chart fetched successively.",
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
                                                    "  \"path\": \"/piechart/v1/Username1/week-chart/20231-13-25\"\n" +
                                                    "}"
                                    )}))            }
    )
    @GetMapping(value = "/{username}/week-chart/{weekDate}")
    ResponseEntity<ChartDTO<String>>  getPieChartOfUserWeekCategoriesExpenses(@PathVariable String username, @PathVariable LocalDate weekDate);

    //TODO: CHANGE ONLY TO CURRENT YEAR!!
    @Operation(
            description =
                    "This operation return pie chart of user expenses by given month . If username doesn't exist in database or " +
                    "user doesn't have any expenses in this month return empty pie chart. In this pie chart, chart segments are expense categories, " +
                    "(App expense categories and user own categories) like: FOOD, HOME, CAR... ",
            summary =
                    "Get user month expenses pie chart",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "month",description = "Month ordinal - value between 1 and 12", example = "11",in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pie chart fetched successively.",
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
                                                    "  \"path\": \"/piechart/v1/Username1/month-chart/13\"\n" +
                                                    "}"
                                    )}))            }
    )
    @GetMapping(value = "/{username}/month-chart/{month}")
    ResponseEntity<ChartDTO<String>>  getPieChartOfUserMonthCategoriesExpenses(@PathVariable String username, @PathVariable @Min(value = 1) @Max(value = 12) int month);

    @Operation(
            description =
                    "This operation return pie chart of user expenses by given year . If username doesn't exist in database or " +
                    "user doesn't have any expenses in this year return empty pie chart. In this pie chart, chart segments are expense categories, " +
                    "(App expense categories and user own categories) like: FOOD, HOME, CAR... ",
            summary =
                    "Get user year expenses pie chart",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "year",description = "Year - value between 2000 and 2050",example = "2020",in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pie chart fetched successively.",
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
                                                    "  \"path\": \"/piechart/v1/Username1/year-chart/13\"\n" +
                                                    "}"
                                    )}))            }
    )
    @GetMapping(value = "/{username}/year-chart/{year}")
    ResponseEntity<ChartDTO<String>>  getPieChartOfUserYearCategoriesExpenses(@PathVariable String username, @PathVariable int year);

    @Operation(
            description =
                    "This operation return pie chart of user expenses by given period . If username doesn't exist in database or " +
                    "user doesn't have any expenses in this period return empty pie chart. In this pie chart, chart segments are expense categories, " +
                    "(App expense categories and user own categories) like: FOOD, HOME, CAR... ",
            summary =
                    "Get user period expenses pie chart",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "from",description = "Start date", example = "2023-11-10",in = ParameterIn.QUERY),
                    @Parameter(name = "to",description = "End date", example = "2023-12-15",in = ParameterIn.QUERY)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pie chart fetched successively.",
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
                                                    "  \"path\": \"/piechart/v1/Username1/period-chart?from=20123-13-25&to=2020-10-12\"\n" +
                                                    "}"
                                    )}))            }
    )
    @GetMapping(value = "/{username}/period-chart",params = {"from","to"})
    ResponseEntity<ChartDTO<String>>  getPieChartOfUserPeriodCategoriesExpenses(@PathVariable String username, @RequestParam LocalDate from, @RequestParam LocalDate to);

}
