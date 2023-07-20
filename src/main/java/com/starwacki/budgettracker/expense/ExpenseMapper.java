package com.starwacki.budgettracker.expense;

import com.starwacki.budgettracker.utils.Mapper;
import org.springframework.stereotype.Component;

@Component
final class ExpenseMapper implements Mapper<Expense, ExpenseDTO> {

    @Override
    public ExpenseDTO mapEntityToDTO(Expense entity) {
        return ExpenseDTO
                .builder()
                .name(entity.getName())
                .date(entity.getDate())
                .time(entity.getTime())
                .description(entity.getDescription())
                .expenseCategory(entity.getExpenseCategory())
                .moneyValue(entity.getMoneyValue())
                .build();
    }

    @Override
    public Expense mapDTOToEntity(ExpenseDTO dto) {
        return Expense
                .builder()
                .name(dto.name())
                .date(dto.date())
                .time(dto.time())
                .description(dto.description())
                .expenseCategory(dto.expenseCategory())
                .moneyValue(dto.moneyValue())
                .build();
    }

}
