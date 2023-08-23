package com.starwacki.budgettracker.expensecategory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(name = "expense-category-operations")
interface ExpenseCategoryOperations {

    @Operation(
            description =
                    "This operation returns all expense categories belonging to user - include " +
                    "app categories and categories created by user. " +
                    "If username doesn't exist or user doesn't have any category return app expense categories list.",
            summary =
                    "Get all user and app expense categories",
            parameters =
            @Parameter(name = "username",description = "Username",example = "Username1",in = ParameterIn.PATH),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Expense Categories fetched successively.",
                            content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                         "[\n" +
                                                 "  {\n" +
                                                 "    \"categoryName\": \"Food\"\n" +
                                                 "  },\n" +
                                                 "  {\n" +
                                                 "    \"categoryName\": \"Home\"\n" +
                                                 "  },\n" +
                                                 "  {\n" +
                                                 "    \"categoryName\": \"Animals\"\n" +
                                                 "  },\n" +
                                                 "  {\n" +
                                                 "    \"categoryName\": \"Car\"\n" +
                                                 "  },\n" +
                                                 "  {\n" +
                                                 "    \"categoryName\": \"Clothes\"\n" +
                                                 "  },\n" +
                                                 "  {\n" +
                                                 "    \"categoryName\": \"Hobby\"\n" +
                                                 "  },\n" +
                                                 "  {\n" +
                                                 "    \"categoryName\": \"Debts\"\n" +
                                                 "  },\n" +
                                                 "  {\n" +
                                                 "    \"categoryName\": \"Education\"\n" +
                                                 "  },\n" +
                                                 "  {\n" +
                                                 "    \"categoryName\": \"Entertainment\"\n" +
                                                 "  },\n" +
                                                 "  {\n" +
                                                 "    \"categoryName\": \"Other\"\n" +
                                                 "  },\n" +
                                                 "  {\n" +
                                                 "    \"categoryName\": \"UserCategory\"\n" +
                                                 "  }\n"+
                                                 "]")}))
            })
    @GetMapping("/{username}")
    ResponseEntity<List<ExpenseCategoryDTO>> getAllCategoriesBelongingToUser(
            @PathVariable String username
    );

    @Operation(
            description =
                    "This operation add expense category by given username. NOTE! " +
                    "User always should authenticated before send this request, because " +
                    "User add expense category to his own USERNAME. Api doesn't check exist of username. ",
            summary =
                    "Add user expense category",
            parameters =
            @Parameter(name = "username",description = "User username",example = "Username1",in = ParameterIn.PATH),
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description =
                            "ExpenseCategoryDTO - contain name, " +
                            " name should have between 3 and 40 characters " +
                            " and should have another name than ",
                    content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(value =
                                  "{\n" +
                                          "  \"categoryName\": \"string\"\n" +
                                          "}")})
            ),
            responses = {
                    @ApiResponse(responseCode = "201",description = "Expense category added successively."),
                    @ApiResponse(responseCode = "400",description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                            "{\n" +
                                                    "  \"timestamp\": \"2023-08-22T12:09:33.404+00:00\",\n" +
                                                    "  \"status\": 400,\n" +
                                                    "  \"error\": \"Bad Request\",\n" +
                                                    "  \"path\": \"/categories/v1/Username1\"\n" +
                                                    "}"
                                    )})),
                    @ApiResponse(responseCode = "409",description = "Expense category already exist. ",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                            "{\n" +
                                                    "  \"timestamp\": \"2023-08-22T19:00:02.044+00:00\",\n" +
                                                    "  \"status\": 409,\n" +
                                                    "  \"error\": \"Conflict\",\n" +
                                                    "  \"path\": \"/categories/v1/Username1\"\n" +
                                                    "}"
                                    )}))
            }

    )
    @PostMapping("/{username}")
    ResponseEntity<Void> addNewUserExpenseCategory(
            @PathVariable String username,
            @Valid @RequestBody ExpenseCategoryDTO expenseCategoryDTO
    );

    @Operation(
            description =
                    "This operation returns all expense categories created by this user " +
                    "If username doesn't exist or user doesn't have any category return empty list.",
            summary =
                    "Get all user expense categories",
            parameters =
            @Parameter(name = "username",description = "Username",example = "Username1",in = ParameterIn.PATH),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Expense Categories fetched successively.",
                            content = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(value =
                                          "[\n" +
                                                  "  {\n" +
                                                  "    \"categoryName\": \"Category1\"\n" +
                                                  "  },\n" +
                                                  "  {\n" +
                                                  "    \"categoryName\": \"Category2\"\n" +
                                                  "  },\n" +
                                                  "  {\n" +
                                                  "    \"categoryName\": \"Category3\"\n" +
                                                  "  }\n" +
                                                  "]")}))
            })
    @GetMapping("/user-categories/{username}")
    ResponseEntity<List<ExpenseCategoryDTO>> getAllUserExpenseCategories(
            @PathVariable String username
    );
}
