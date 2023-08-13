package com.starwacki.budgettracker.registration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceUnitTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private UserRepository userRepository;



    @Test
    @DisplayName("Test registerUser shouldn't register user when username already exist")
    void shouldDontRegisterUser_whenUsernameExist() {

        //given
        String existUsername = "exist_username";
        String existEmail = "no_exist_email";
        UserDTO userDTO = new UserDTO(existUsername,existEmail,"password");
        given(userRepository.existsByUsername(existUsername)).willReturn(true);

        //then
        assertThrows(UserOrEmailAlreadyExist.class,() -> registrationService.registerUser(userDTO));
    }

    @Test
    @DisplayName("Test registerUser shouldn't register user when email already exist")
    void shouldDontRegisterUser_whenEmailExist() {

        //given
        String existUsername = "no_exist_username";
        String existEmail = "exist_email";
        UserDTO userDTO = new UserDTO(existUsername,existEmail,"password");
        given(userRepository.existsByUsername(existUsername)).willReturn(false);
        given(userRepository.existsByEmail(existEmail)).willReturn(true);


        //then
        assertThrows(UserOrEmailAlreadyExist.class,() -> registrationService.registerUser(userDTO));
    }

    @Test
    @DisplayName("Test registerUser should register user")
    void shouldRegisterUser() {

        //given
        String existUsername = "no_exist_username";
        String existEmail = "no_exist_email";
        UserDTO userDTO = new UserDTO(existUsername,existEmail,"password");
        given(userRepository.existsByUsername(existUsername)).willReturn(false);
        given(userRepository.existsByEmail(existEmail)).willReturn(false);

        //when
        registrationService.registerUser(userDTO);

        //then
        verify(userRepository).save(any());
    }



}