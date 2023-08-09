package com.starwacki.budgettracker.expensecategory;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseCategoryQueryRepository expenseCategoryQueryRepository;
    void addNewUserExpenseCategory(String username, ExpenseCategoryDTO dto) {
        if (doesExpenseCategoryCanBeCreated(dto.categoryName()))
            expenseCategoryRepository.save(ExpenseCategoryMapper.mapDtoToEntity(username,dto));
        else
            throw new ExistExpenseCategoryException(dto.categoryName());
    }

    List<ExpenseCategoryDTO> getAllCategoriesBelongingToUser(String username) {
        List<ExpenseCategoryDTO> userCategories = getAllUserCategories(username);
        List<ExpenseCategoryDTO> appCategories = getAppCategories();
        return getCombinatedList(userCategories,appCategories);
    }

    private List<ExpenseCategoryDTO> getCombinatedList(List<ExpenseCategoryDTO> userCategories,
                                                       List<ExpenseCategoryDTO> appCategories) {
        List<ExpenseCategoryDTO> combinedCategories = new ArrayList<>();
        combinedCategories.addAll(userCategories);
        combinedCategories.addAll(appCategories);
        return combinedCategories;
    }

    private List<ExpenseCategoryDTO> getAllUserCategories(String username) {
        return expenseCategoryQueryRepository.findAllExpenseCategoriesCreatedByUser(username);
    }

   private List<ExpenseCategoryDTO> getAppCategories() {
        return Arrays.stream(ExpenseCategory.values())
                .toList()
                .stream()
                .map(ExpenseCategoryMapper::mapEnumToDTO)
                .toList();
   }

    private boolean doesExpenseCategoryCanBeCreated(String categoryName) {
        return !doesExpenseCategoryExistInAppCategories(categoryName)
                && !doesUserHaveExpenseCategory(categoryName);
    }

    private boolean doesUserHaveExpenseCategory(String categoryName) {
        return expenseCategoryRepository.existsByCategoryName(categoryName);
    }

    private boolean doesExpenseCategoryExistInAppCategories(String categoryName) {
        return Arrays.stream(ExpenseCategory.values()).map(ExpenseCategory::getCategoryName)
                .collect(Collectors.toSet())
                .contains(categoryName);
    }

}
