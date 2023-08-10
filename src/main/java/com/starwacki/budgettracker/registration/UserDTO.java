package com.starwacki.budgettracker.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

record UserDTO(
        @NotBlank @Length(min = 6,max = 20)
        String username,
        @Email
        String email,
        @Pattern
                (regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,25}$\n",
                 message = "Password must include: \n" +
                  "*At least 6 characters" +
                  "*Minimum one small letter \n" +
                  "*Minimum one big letter \n" +
                  "*Minimum one number \n")
        String password
) {
}
