package com.starwacki.budgettracker.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

record UserDTO(

        @Length(min = 6,max = 25)
        @Pattern(regexp = "^\\S*$")
        String username,
        @Email
        String email,

        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,25}$",
        message = "Password must include: \n" +
                  "At least 6 characters \n" +
                  "Maximum 25 characters" +
                  "Minimum one small letter \n" +
                  "Minimum one big letter \n " +
                  "Minimum one number \n " +
                  "Minimum one special character: #?!@$%^&*-")
        String password
) {
}
