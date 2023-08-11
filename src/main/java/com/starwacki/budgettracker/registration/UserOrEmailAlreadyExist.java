package com.starwacki.budgettracker.registration;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
class UserOrEmailAlreadyExist extends RuntimeException {

    UserOrEmailAlreadyExist() {
        super("User with given username or email already exist");
    }


}
