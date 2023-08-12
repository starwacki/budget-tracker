package com.starwacki.budgettracker.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ActiveProfiles("tests")
@AutoConfigureMockMvc
class RegistrationControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private static final String ENDPOINT_REQUEST_MAPPING = "/register";

    @ParameterizedTest
    @ValueSource(strings = {"short","","322","  ","username longer than 25 characters","With spaces"})
    @DisplayName("Test register() given wrong username shouldn't register user")
    @Sql("classpath:insert_users.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return400StatusCode_AndThrowValidException_whenUsernameIsWrong(String username) throws Exception {

        //given
        UserDTO userDTO =  new UserDTO(username,"email@wp.pl","Password1.");

        //when
        int beforePostUsersSize = userRepository.findAll().size();

        //when
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/v1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(equalTo(HttpStatus.BAD_REQUEST.value()))))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

        int afterPostUsersSize = userRepository.findAll().size();

        assertThat(beforePostUsersSize,is(equalTo(afterPostUsersSize)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"","       ","So1.t","Ab1321323","1234567","TooLongPassword1212112212112121"})
    @DisplayName("Test register() given wrong password shouldn't register user")
    @Sql("classpath:insert_users.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return400StatusCode_AndThrowValidException_whenPasswordIsWrong(String password) throws Exception {

        //given
        UserDTO userDTO =  new UserDTO("username","email@wp.pl",password);

        //when
        int beforePostUsersSize = userRepository.findAll().size();

        //when
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/v1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(equalTo(HttpStatus.BAD_REQUEST.value()))))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

        int afterPostUsersSize = userRepository.findAll().size();

        assertThat(beforePostUsersSize,is(equalTo(afterPostUsersSize)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"email.pl"," ","@.com","    "})
    @DisplayName("Test register() given wrong email shouldn't register user")
    @Sql("classpath:insert_users.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return400StatusCode_AndThrowValidException_whenEmailIsWrong(String email) throws Exception {

        //given
        UserDTO userDTO =  new UserDTO("username",email,"Pass123.");

        //when
        int beforePostUsersSize = userRepository.findAll().size();

        //when
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/v1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(equalTo(HttpStatus.BAD_REQUEST.value()))))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

        int afterPostUsersSize = userRepository.findAll().size();

        assertThat(beforePostUsersSize,is(equalTo(afterPostUsersSize)));
    }

    @Test
    @DisplayName("Test register() should register user")
    @Sql("classpath:insert_users.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return204StatusCode_AndRegisterUser() throws Exception {

        //given
        UserDTO userDTO =  new UserDTO("johnsnow1","goodemail@wp.pl","Pass123%");

        //when
        int beforePostUsersSize = userRepository.findAll().size();

        //when
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/v1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(equalTo(HttpStatus.CREATED.value()))));

        int afterPostUsersSize = userRepository.findAll().size();

        assertThat(beforePostUsersSize,is(not(equalTo(afterPostUsersSize))));
    }

    @Test
    @DisplayName("Test register() shouldn't register user when username already exist")
    @Sql("classpath:insert_users.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return408StatusCode_AndNotRegisterUser_WhenUsernameExist() throws Exception {

        //given
        UserDTO userDTO =  new UserDTO("username1","goodemail@wp.pl","Pass123%");

        //when
        int beforePostUsersSize = userRepository.findAll().size();

        //when
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/v1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(equalTo(HttpStatus.CONFLICT.value()))))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserOrEmailAlreadyExist));

        int afterPostUsersSize = userRepository.findAll().size();

        assertThat(beforePostUsersSize,is(equalTo(afterPostUsersSize)));
    }

    @Test
    @DisplayName("Test register() shouldn't register user when email already exist")
    @Sql("classpath:insert_users.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return408StatusCode_AndNotRegisterUser_WhenEmailExist() throws Exception {

        //given
        UserDTO userDTO =  new UserDTO("username1","email1@wp.pl","Pass123%");

        //when
        int beforePostUsersSize = userRepository.findAll().size();

        //when
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/v1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(equalTo(HttpStatus.CONFLICT.value()))))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserOrEmailAlreadyExist));

        int afterPostUsersSize = userRepository.findAll().size();

        assertThat(beforePostUsersSize,is(equalTo(afterPostUsersSize)));
    }






}
