package com.starwacki.budgettracker.registration;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/v1/")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        registrationService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
