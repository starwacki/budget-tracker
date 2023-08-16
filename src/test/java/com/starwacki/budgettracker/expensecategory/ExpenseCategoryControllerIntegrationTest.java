package com.starwacki.budgettracker.expensecategory;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseCategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    private ExpenseCategoryQueryRepository expenseCategoryQueryRepository;

    private static final String ENDPOINT_REQUEST_MAPPING = "/categories";


    @Test
    @DisplayName("Test getAllCategoriesBelongingToUser() when user doesn't have own category return only app categories")
    @Sql("classpath:insert_user_expense_categories.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return200HttpStatus_AndListWithAppCategories() throws Exception {

        //given
        String username = "user_without_his_own_categories";

        //when
        List<ExpenseCategoryDTO> expectedCategoriesList = Arrays
                .stream(ExpenseCategory.values()).map(ExpenseCategoryMapper::mapEnumToDTO).toList();

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(equalTo(HttpStatus.OK.value()))))
                .andExpect(result -> {

                    TypeReference<List<ExpenseCategoryDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseCategoryDTO> acutalCategoriesList = objectMapper
                            .readValue(result.getResponse().getContentAsString(),typeReference);

                    assertThat(expectedCategoriesList,is(equalTo(acutalCategoriesList)));
                });
    }

    @Test
    @DisplayName("Test getAllCategoriesBelongingToUser() when user have own category return categories list")
    @Sql("classpath:insert_user_expense_categories.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return200HttpStatus_AndListWithMixedCategories() throws Exception {

        //given
        String username = "user2";

        //when
        List<ExpenseCategoryDTO> expectedCategoriesList = new ArrayList<>();

        List<ExpenseCategoryDTO> appCategories = Arrays
                .stream(ExpenseCategory.values()).map(ExpenseCategoryMapper::mapEnumToDTO).toList();
        List<ExpenseCategoryDTO> userCategories = List
                .of(new ExpenseCategoryDTO("category4"));
        expectedCategoriesList.addAll(appCategories);
        expectedCategoriesList.addAll(userCategories);


        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/"+username))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(equalTo(HttpStatus.OK.value()))))
                .andExpect(result -> {

                    TypeReference<List<ExpenseCategoryDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseCategoryDTO> acutalCategoriesList = objectMapper
                            .readValue(result.getResponse().getContentAsString(),typeReference);

                    Set<ExpenseCategoryDTO> expectedCategoriesSet = new HashSet<>(expectedCategoriesList);
                    Set<ExpenseCategoryDTO> actualCategoriesSet = new HashSet<>(acutalCategoriesList);

                    assertEquals(expectedCategoriesSet,actualCategoriesSet);
                });

    }

    @Test
    @DisplayName("Test addNewUserExpenseCategory() should add new expense category to user")
    @Sql("classpath:insert_user_expense_categories.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return204HttpStatus_AndAddNewExpenseCategory() throws Exception {

        //given
        String username = "user2";
        ExpenseCategoryDTO expenseCategoryDTO = new ExpenseCategoryDTO("new_category");

        //when
        int beforePostUsernameExpenseCategories = expenseCategoryQueryRepository
                .findAllExpenseCategoriesCreatedByUser(username).size();

        //then
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/v1/"+username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseCategoryDTO)))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.CREATED.value())));

        int afterPostUsernameExpenseCategories = expenseCategoryQueryRepository
                .findAllExpenseCategoriesCreatedByUser(username).size();

        assertThat(beforePostUsernameExpenseCategories,is(not(afterPostUsernameExpenseCategories)));
    }

    @Test
    @DisplayName("Test addNewUserExpenseCategory() should return 408 HTTP Code and don't add category when user has this category")
    @Sql("classpath:insert_user_expense_categories.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return408HttpStatus_AndDoNotAddExpenseCategory_WhenAddUserExpenseCategory() throws Exception {

        //given
        String username = "user2";
        ExpenseCategoryDTO expenseCategoryDTO = new ExpenseCategoryDTO("category4");

        //when
        int beforePostUsernameExpenseCategories = expenseCategoryQueryRepository
                .findAllExpenseCategoriesCreatedByUser(username).size();

        //then
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/v1/"+username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseCategoryDTO)))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.CONFLICT.value())));

        int afterPostUsernameExpenseCategories = expenseCategoryQueryRepository
                .findAllExpenseCategoriesCreatedByUser(username).size();

        assertThat(beforePostUsernameExpenseCategories,is(afterPostUsernameExpenseCategories));
    }

    @Test
    @DisplayName("Test addNewUserExpenseCategory() should return 408 HTTP Code and don't add category when user has this category")
    @Sql("classpath:insert_user_expense_categories.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return408HttpStatus_AndDoNotAddExpenseCategory_WhenAddAppExpenseCategory() throws Exception {

        //given
        String username = "user2";
        ExpenseCategoryDTO expenseCategoryDTO = new ExpenseCategoryDTO("Other");

        //when
        int beforePostUsernameExpenseCategories = expenseCategoryQueryRepository
                .findAllExpenseCategoriesCreatedByUser(username).size();

        //then
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/v1/"+username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseCategoryDTO)))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.CONFLICT.value())));

        int afterPostUsernameExpenseCategories = expenseCategoryQueryRepository
                .findAllExpenseCategoriesCreatedByUser(username).size();

        assertThat(beforePostUsernameExpenseCategories,is(afterPostUsernameExpenseCategories));
    }

    @Test
    @DisplayName("Test addNewUserExpenseCategory() should return 400 HTTP Code and don't add category when category name is empty")
    @Sql("classpath:insert_user_expense_categories.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return400HttpStatus_AndDoNotAddExpenseCategory_WhenGivenBlankName() throws Exception {

        //given
        String username = "user2";
        ExpenseCategoryDTO expenseCategoryDTO = new ExpenseCategoryDTO("");

        //when
        int beforePostUsernameExpenseCategories = expenseCategoryQueryRepository
                .findAllExpenseCategoriesCreatedByUser(username).size();

        //then
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/v1/"+username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseCategoryDTO)))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.BAD_REQUEST.value())));

        int afterPostUsernameExpenseCategories = expenseCategoryQueryRepository
                .findAllExpenseCategoriesCreatedByUser(username).size();

        assertThat(beforePostUsernameExpenseCategories,is(afterPostUsernameExpenseCategories));
    }

    @ParameterizedTest
    @ValueSource(strings = {""," ","         ", "12", "StringWithMoreThan40Charactersxxxxxxxxxxxxxxxxxxx"})
    @DisplayName("Test addNewUserExpenseCategory() should return 400 HTTP Code and don't add category when category name has bad length")
    @Sql("classpath:insert_user_expense_categories.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return400HttpStatus_AndDoNotAddExpenseCategory_WhenGivenTooShortName(String name) throws Exception {

        //given
        String username = "user2";
        ExpenseCategoryDTO expenseCategoryDTO = new ExpenseCategoryDTO(name);

        //when
        int beforePostUsernameExpenseCategories = expenseCategoryQueryRepository
                .findAllExpenseCategoriesCreatedByUser(username).size();

        //then
        mockMvc.perform(post(ENDPOINT_REQUEST_MAPPING+"/v1/"+username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseCategoryDTO)))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.BAD_REQUEST.value())));

        int afterPostUsernameExpenseCategories = expenseCategoryQueryRepository
                .findAllExpenseCategoriesCreatedByUser(username).size();

        assertThat(beforePostUsernameExpenseCategories,is(afterPostUsernameExpenseCategories));
    }

    @Test
    @DisplayName("Test getAllUserExpenseCategories() should return 200 HTTP Code and empty list when user doesn't have expense categories")
    @Sql("classpath:insert_user_expense_categories.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return200HttpStatus_AndEmptyList() throws Exception {

        //given
        String username = "user_without_expense_categories";

        //when
        List<ExpenseCategoryDTO> expectedUsernameCategories  = List.of();

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/user/"+username))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<List<ExpenseCategoryDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseCategoryDTO> actualUsernameCategories = objectMapper.readValue(result.getResponse().getContentAsString(),
                            typeReference);

                    assertThat(expectedUsernameCategories,is(equalTo(actualUsernameCategories)));

                });

    }

    @Test
    @DisplayName("Test getAllUserExpenseCategories() should return 200 HTTP Code and list when user have expense categories")
    @Sql("classpath:insert_user_expense_categories.sql")
    @Sql(scripts = "classpath:clean-test-database.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_Return200HttpStatus_AndList() throws Exception {

        //given
        String username = "user1";

        //when
        List<ExpenseCategoryDTO> expectedUsernameCategories  = List.of(
                new ExpenseCategoryDTO("category1"),
                new ExpenseCategoryDTO("category2"),
                new ExpenseCategoryDTO("category3")
        );

        //then
        mockMvc.perform(get(ENDPOINT_REQUEST_MAPPING+"/v1/user/"+username))
                .andExpect(result -> assertThat(result.getResponse().getStatus(),is(HttpStatus.OK.value())))
                .andExpect(result -> {

                    TypeReference<List<ExpenseCategoryDTO>> typeReference = new TypeReference<>() {};
                    List<ExpenseCategoryDTO> actualUsernameCategories = objectMapper.readValue(result.getResponse().getContentAsString(),
                            typeReference);

                    assertThat(expectedUsernameCategories,is(equalTo(actualUsernameCategories)));

                });
    }




}
