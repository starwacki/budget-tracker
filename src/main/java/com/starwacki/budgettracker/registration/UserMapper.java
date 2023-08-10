package com.starwacki.budgettracker.registration;

final class UserMapper {

    private UserMapper() {}

    public static User mapDtoToEntity(UserDTO userDTO) {
        return User
                .builder()
                .email(userDTO.email())
                .password(userDTO.password())
                .username(userDTO.username())
                .build();
    }
}
