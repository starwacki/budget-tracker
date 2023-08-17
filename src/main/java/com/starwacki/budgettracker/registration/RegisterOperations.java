package com.starwacki.budgettracker.registration;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "register-operations")
interface RegisterOperations {

    @PostMapping("/v1/")
    ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO);
}
