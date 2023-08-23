package com.starwacki.budgettracker.expense;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "expense-operations")
interface ExpenseOperations {

    @Operation(
            description =
                    "This operation returns expense by given id",
            summary =
                    "Get expense",
            parameters =
                    @Parameter(name = "id",description = "Expense id",example = "1",in = ParameterIn.PATH),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Expense fetched successively.",
                            content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                                       "{\n" +
                                                          "  \"id\": 1,\n" +
                                                          "  \"name\": \"Object name\",\n" +
                                                          "  \"description\": \"This is description\",\n" +
                                                          "  \"expenseCategory\": \"FOOD\",\n" +
                                                          "  \"date\": \"2023-08-16\",\n" +
                                                          "  \"time\": \"15:40:13\",\n" +
                                                          "  \"moneyValue\": 100\n" +
                                                          "}")})),
                    @ApiResponse(responseCode = "404",description = "Expense not found.",
                            content = @Content(contentSchema = @Schema(implementation = ExpenseDTO.class,contentMediaType = MediaType.APPLICATION_JSON_VALUE),
                                    examples = {@ExampleObject(value = "Expense not found")}))
            })
    @GetMapping("/id/{id}")
    ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable Long id);

    @Operation(
            description =
                    "This operation add expense by given username. NOTE!" +
                    "User always should authenticated before send this request, because" +
                    "User add expense to his own USERNAME. Api doesn't check exist of username. ",
            summary =
                    "Add expense",
            parameters =
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        required = true,
                        description = "ExpenseDTO - user shouldn't be able to add expense id in dto! ",
                        content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                examples = {@ExampleObject(value =
                                    "{\n" +
                                            "  \"name\": \"Object name\",\n" +
                                            "  \"description\": \"This is description\",\n" +
                                            "  \"expenseCategory\": \"FOOD\",\n" +
                                            "  \"date\": \"2023-08-16\",\n" +
                                            "  \"time\": \"15:40:13\",\n" +
                                            "  \"moneyValue\": 150\n" +
                                            "}")})
            ),
            responses = {
                    @ApiResponse(responseCode = "201",description = "Expense added successively."),
            }

    )
    @PostMapping("/{username}")
    ResponseEntity<Void> addNewExpenseToUser(@RequestBody @Valid ExpenseDTO expenseDTO, @PathVariable String username);

    @Operation(
            description =
                    "This operation update exist expense by given id",
            summary =
                    "Update expense",
            parameters =
                    @Parameter(name = "id",description = "Expense id",example = "1",in = ParameterIn.PATH),
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                          required = true,
                          description = "ExpenseDTO - user shouldn't be able to change expense id or username!",
                          content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  examples = {@ExampleObject(value =
                                    "{\n" +
                                            "  \"name\": \"Object name\",\n" +
                                            "  \"description\": \"This is description\",\n" +
                                            "  \"expenseCategory\": \"FOOD\",\n" +
                                            "  \"date\": \"2023-08-16\",\n" +
                                            "  \"time\": \"15:40:13\",\n" +
                                            "  \"moneyValue\": 150\n" +
                                            "}")})
                          ),
            responses = {
                    @ApiResponse(responseCode = "204",description = "Expense updated successively."),
                    @ApiResponse(responseCode = "404",description = "The updated expense should exist. This code is returned when something is wrong.")
            }
    )
    @PutMapping("/id/{id}")
    ResponseEntity<Void> updateExpenseById(@PathVariable Long id, @RequestBody ExpenseDTO updatedExpenseDTO);

    @Operation(
            description =
                    "This operation delete exist expense by given id",
            summary =
                    "delete expense",
            parameters =
                    @Parameter(name = "id",description = "Expense id",example = "1",in = ParameterIn.PATH),
            responses = {
                    @ApiResponse(responseCode = "204",description = "Expense deleted successively."),
                    @ApiResponse(responseCode = "404",description = "The deleted expense should exist!. This code is returned when something is wrong."),

            })
    @DeleteMapping("/id/{id}")
    ResponseEntity<Void> deleteExpenseById(@PathVariable Long id);

    @Operation(
            description =
                    "This operation return list of user expenses. If username doesn't exist in database return empty list.",
            summary =
                    "Get user expenses",
            parameters =
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
            responses =
                    @ApiResponse(responseCode = "200",description = "Expenses fetched successively.",
                            content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                    "[\n" +
                                            "  {\n" +
                                            "    \"id\": 2,\n" +
                                            "    \"name\": \"Object name\",\n" +
                                            "    \"description\": \"This is description\",\n" +
                                            "    \"expenseCategory\": \"FOOD\",\n" +
                                            "    \"date\": \"2023-08-16\",\n" +
                                            "    \"time\": \"15:40:13\",\n" +
                                            "    \"moneyValue\": 150\n" +
                                            "  },\n" +
                                            "  {\n" +
                                            "    \"id\": 3,\n" +
                                            "    \"name\": \"Object name2\",\n" +
                                            "    \"description\": \"This is description\",\n" +
                                            "    \"expenseCategory\": \"HOME\",\n" +
                                            "    \"date\": \"2023-08-16\",\n" +
                                            "    \"time\": \"15:40:13\",\n" +
                                            "    \"moneyValue\": 150\n" +
                                            "  },\n" +
                                            "  {\n" +
                                            "    \"id\": 4,\n" +
                                            "    \"name\": \"Object name3\",\n" +
                                            "    \"description\": \"This is description\",\n" +
                                            "    \"expenseCategory\": \"ANIMALS\",\n" +
                                            "    \"date\": \"2023-08-16\",\n" +
                                            "    \"time\": \"15:40:13\",\n" +
                                            "    \"moneyValue\": 150\n" +
                                            "  }\n" +
                                            "]")})
            )
    )
    @GetMapping("/{username}")
    ResponseEntity<List<ExpenseDTO>> findAllUsernameExpenses(@PathVariable String username);

    @Operation(
            description =
                    "This operation return list of user expenses by given expense category. If username or category" +
                    " doesn't exist in database return empty list.",
            summary =
                    "Get user category expenses",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "category",description = "Expense category",example = "FOOD",in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expenses fetched successively.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                            "[\n" +
                                                    "  {\n" +
                                                    "    \"id\": 2,\n" +
                                                    "    \"name\": \"Object name\",\n" +
                                                    "    \"description\": \"This is description\",\n" +
                                                    "    \"expenseCategory\": \"FOOD\",\n" +
                                                    "    \"date\": \"2023-08-16\",\n" +
                                                    "    \"time\": \"15:40:13\",\n" +
                                                    "    \"moneyValue\": 150\n" +
                                                    "  },\n" +
                                                    "  {\n" +
                                                    "    \"id\": 3,\n" +
                                                    "    \"name\": \"Object name2\",\n" +
                                                    "    \"description\": \"This is description\",\n" +
                                                    "    \"expenseCategory\": \"FOOD\",\n" +
                                                    "    \"date\": \"2023-08-16\",\n" +
                                                    "    \"time\": \"15:40:13\",\n" +
                                                    "    \"moneyValue\": 150\n" +
                                                    "  },\n" +
                                                    "  {\n" +
                                                    "    \"id\": 4,\n" +
                                                    "    \"name\": \"Object name3\",\n" +
                                                    "    \"description\": \"This is description\",\n" +
                                                    "    \"expenseCategory\": \"FOOD\",\n" +
                                                    "    \"date\": \"2023-08-16\",\n" +
                                                    "    \"time\": \"15:40:13\",\n" +
                                                    "    \"moneyValue\": 150\n" +
                                                    "  }\n" +
                                                    "]")}))
            }
    )
    @GetMapping("/{username}/category/{category}")
    ResponseEntity<List<ExpenseDTO>> findAllExpensesByUsernameAndExpenseCategory(@PathVariable String username,@PathVariable String category);

    @Operation(
            description =
                    "This operation return list of user expenses by given date. If username doesn't exist in database or " +
                    "user doesn't have any expenses in this date return empty list.",
            summary =
                    "Get user date expenses",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "date",description = "Date: yyyy-mm-dd",example = "2023-07-05",in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expenses fetched successively.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                    "[\n" +
                                            "  {\n" +
                                            "    \"id\": 2,\n" +
                                            "    \"name\": \"Object name\",\n" +
                                            "    \"description\": \"This is description\",\n" +
                                            "    \"expenseCategory\": \"FOOD\",\n" +
                                            "    \"date\": \"2023-07-05\",\n" +
                                            "    \"time\": \"15:40:13\",\n" +
                                            "    \"moneyValue\": 150\n" +
                                            "  }]")})),
                    @ApiResponse(responseCode = "400",description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                            "{\n" +
                                                    "  \"timestamp\": \"2023-08-22T12:09:33.404+00:00\",\n" +
                                                    "  \"status\": 400,\n" +
                                                    "  \"error\": \"Bad Request\",\n" +
                                                    "  \"path\": \"/expenses/v1/Username1/date/20231-13-25\"\n" +
                                                    "}"
                                    )}))
            }
    )
    @GetMapping("/{username}/date/{date}")
    ResponseEntity<List<ExpenseDTO>> findAllDayExpenses(@PathVariable String username, @PathVariable LocalDate date);

    @Operation(
            description =
                    "This operation return list of user expenses by given week . If username doesn't exist in database or " +
                    "user doesn't have any expenses in this week return empty list.",
            summary =
                    "Get user week expenses",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "weekDate",description = "It's any date in week. For example: we have one week: start in 2023-08-21 and" +
                            "end in 2023-08-27, so we can give any date from this period and we will get list of this week expenses",
                                  example = "2023-08-25",in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expenses fetched successively.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                    "[\n" +
                                            "  {\n" +
                                            "    \"id\": 2,\n" +
                                            "    \"name\": \"Object name\",\n" +
                                            "    \"description\": \"This is description\",\n" +
                                            "    \"expenseCategory\": \"FOOD\",\n" +
                                            "    \"date\": \"2023-08-21\",\n" +
                                            "    \"time\": \"15:40:13\",\n" +
                                            "    \"moneyValue\": 150\n" +
                                            "  }]")})),
                    @ApiResponse(responseCode = "400",description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                    "{\n" +
                                            "  \"timestamp\": \"2023-08-22T12:09:33.404+00:00\",\n" +
                                            "  \"status\": 400,\n" +
                                            "  \"error\": \"Bad Request\",\n" +
                                            "  \"path\": \"/expenses/v1/Username1/week/20231-13-25\"\n" +
                                            "}"
                            )}))            }
    )
    @GetMapping("/{username}/week/{weekDate}")
    ResponseEntity<List<ExpenseDTO>> findAllWeekExpenses(@PathVariable String username, @PathVariable LocalDate weekDate);

    //TODO: CHANGE ONLY TO CURRENT YEAR!!
    @Operation(
            description =
                    "This operation return list of user expenses by given month . If username doesn't exist in database or " +
                    "user doesn't have any expenses in this month return empty list.",
            summary =
                    "Get user month expenses",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "month",description = "Month ordinal - value between 1 and 12", example = "11",in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expenses fetched successively.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                    "[\n" +
                                            "  {\n" +
                                            "    \"id\": 2,\n" +
                                            "    \"name\": \"Object name\",\n" +
                                            "    \"description\": \"This is description\",\n" +
                                            "    \"expenseCategory\": \"FOOD\",\n" +
                                            "    \"date\": \"2023-11-21\",\n" +
                                            "    \"time\": \"15:40:13\",\n" +
                                            "    \"moneyValue\": 150\n" +
                                            "  }]")})),
                    @ApiResponse(responseCode = "400",description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                examples = {@ExampleObject(value =
                                    "{\n" +
                                            "  \"timestamp\": \"2023-08-22T12:09:33.404+00:00\",\n" +
                                            "  \"status\": 400,\n" +
                                            "  \"error\": \"Bad Request\",\n" +
                                            "  \"path\": \"/expenses/v1/Username1/month/2023-13-25\"\n" +
                                            "}"
                            )}))            }
    )
    @GetMapping("/{username}/month/{month}")
    ResponseEntity<List<ExpenseDTO>> findAllMonthExpenses(@PathVariable String username, @PathVariable @Min(value = 1) @Max(value = 12) int month);

    @Operation(
            description =
                    "This operation return list of user expenses by given year. If username doesn't exist in database or " +
                    "user doesn't have any expenses in this year return empty list.",
            summary =
                    "Get user month expenses",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "year",description = "Year - value between 2000 and 2050",example = "2020",in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expenses fetched successively.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                    "[\n" +
                                            "  {\n" +
                                            "    \"id\": 2,\n" +
                                            "    \"name\": \"Object name\",\n" +
                                            "    \"description\": \"This is description\",\n" +
                                            "    \"expenseCategory\": \"FOOD\",\n" +
                                            "    \"date\": \"2022-11-21\",\n" +
                                            "    \"time\": \"15:40:13\",\n" +
                                            "    \"moneyValue\": 150\n" +
                                            "  }]")})),
                    @ApiResponse(responseCode = "400",description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                    "{\n" +
                                            "  \"timestamp\": \"2023-08-22T12:09:33.404+00:00\",\n" +
                                            "  \"status\": 400,\n" +
                                            "  \"error\": \"Bad Request\",\n" +
                                            "  \"path\": \"/expenses/v1/Username1/year/30000\"\n" +
                                            "}"
                            )}))
            }
    )
    @GetMapping("/{username}/year/{year}")
    ResponseEntity<List<ExpenseDTO>> findAllYearExpenses(@PathVariable String username, @PathVariable @Min(value = 2000) @Max(value = 2050) int year);

    @Operation(
            description =
                    "This operation return list of user expenses by given period . If username doesn't exist in database or " +
                    "user doesn't have any expenses in this period return empty list.",
            summary =
                    "Get user month expenses",
            parameters = {
                    @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
                    @Parameter(name = "from",description = "Start date", example = "2023-11-10",in = ParameterIn.QUERY),
                    @Parameter(name = "to",description = "End date", example = "2023-12-15",in = ParameterIn.QUERY)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expenses fetched successively.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                    "[\n" +
                                            "  {\n" +
                                            "    \"id\": 2,\n" +
                                            "    \"name\": \"Object name\",\n" +
                                            "    \"description\": \"This is description\",\n" +
                                            "    \"expenseCategory\": \"FOOD\",\n" +
                                            "    \"date\": \"2023-11-21\",\n" +
                                            "    \"time\": \"15:40:13\",\n" +
                                            "    \"moneyValue\": 150\n" +
                                            "  }]")})),
                    @ApiResponse(responseCode = "400",description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                    "{\n" +
                                            "  \"timestamp\": \"2023-08-22T12:09:33.404+00:00\",\n" +
                                            "  \"status\": 400,\n" +
                                            "  \"error\": \"Bad Request\",\n" +
                                            "  \"path\": \"/expenses/v1/Username1/period?from=20123-13-25&to=2020-10-12\"\n" +
                                            "}"
                                    )}))
            }
    )
    @GetMapping(value = "/{username}/period",params = {"from","to"})
    ResponseEntity<List<ExpenseDTO>> findAllPeriodExpenses(@PathVariable String username, @RequestParam LocalDate from, @RequestParam LocalDate to);

}
