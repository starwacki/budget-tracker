package com.starwacki.budgettracker.expensecategory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@ActiveProfiles("tests")
class ExpenseCategoryQueryRepositoryUnitTest {

    @Autowired
    private ExpenseCategoryQueryRepository expenseCategoryQueryRepository;

    @Test
    @DisplayName("Test findAllExpenseCategoriesCreatedByUser() when user doesn't have any expense category return empty list")
    @Sql("classpath:insert_user_expense_categories.sql")
    void should_ReturnEmptyList() {

        //given
        String username = "user_without_expense_categories";

        //when
        int actualUserExpenseCategoriesSize = expenseCategoryQueryRepository.findAllExpenseCategoriesCreatedByUser(username).size();

        //then
        int expectedExpenseCategoriesSize = 0;
        assertThat(expectedExpenseCategoriesSize,is(equalTo(actualUserExpenseCategoriesSize)));
    }

    @Test
    @DisplayName("Test findAllExpenseCategoriesCreatedByUser() when user has expense categories return list")
    @Sql("classpath:insert_user_expense_categories.sql")
    void should_ReturnListWithExpenseCategories() {

        //given
        String username = "user1";

        //when
        List<ExpenseCategoryDTO> actualExpenseCategoryList = expenseCategoryQueryRepository.findAllExpenseCategoriesCreatedByUser(username);

        //then
        int expectedExpenseCategoriesSize = 3;
        List<ExpenseCategoryDTO> expectedExpenseCategorylist = List.of(
                new ExpenseCategoryDTO("category1"),new ExpenseCategoryDTO("category2"),
                new ExpenseCategoryDTO("category3"));
        assertThat(expectedExpenseCategoriesSize,is(equalTo(actualExpenseCategoryList.size())));
        assertThat(expectedExpenseCategorylist,is(equalTo(actualExpenseCategoryList)));

    }

}
