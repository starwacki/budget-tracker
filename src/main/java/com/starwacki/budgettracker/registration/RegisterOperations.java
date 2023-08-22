package com.starwacki.budgettracker.registration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "register-operations")
interface RegisterOperations {


    @Operation(
            description =
                    "Register new user",
            summary =
                    "Register user",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            required = true,
                            description = "UserDTO - user information",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(value =
                                            "{\n" +
                                                    "  \"username\": \"Username1\",\n" +
                                                    "  \"email\": \"email1@wp.pl\",\n" +
                                                    "  \"password\": \"Password3.\"\n" +
                                                    "}"
                                           )})
                    ),
            responses = {
                    @ApiResponse(responseCode = "201",description = "User register successively."),
                    @ApiResponse(responseCode = "400",description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(value =
                                            "{\n" +
                                                    "  \"timestamp\": \"2023-08-22T12:09:33.404+00:00\",\n" +
                                                    "  \"status\": 400,\n" +
                                                    "  \"error\": \"Bad Request\",\n" +
                                                    "  \"path\": \"/register/v1/\"\n" +
                                                    "}"
                                    )})),
                    @ApiResponse(responseCode = "409",description = "Username or email exist.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(value =
                                           "{\n" +
                                                   "  \"timestamp\": \"2023-08-22T19:00:02.044+00:00\",\n" +
                                                   "  \"status\": 409,\n" +
                                                   "  \"error\": \"Conflict\",\n" +
                                                   "  \"path\": \"/register/v1/\"\n" +
                                                   "}"
                                    )}))
            }
    )
    @PostMapping("/v1/")
    ResponseEntity<Void> register(@Valid @RequestBody UserDTO userDTO);
}
