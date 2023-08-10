package com.starwacki.budgettracker.registration;

class UserOrEmailAlreadyExist extends RuntimeException {

    UserOrEmailAlreadyExist() {
        super("User with given username or email already exist");
    }


}
