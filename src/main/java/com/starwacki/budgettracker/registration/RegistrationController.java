package com.starwacki.budgettracker.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
class RegistrationController implements RegisterOperations {

    private final RegistrationService registrationService;

    @Override
    public ResponseEntity<?> register(UserDTO userDTO) {
        registrationService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
