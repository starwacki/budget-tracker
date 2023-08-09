package com.starwacki.budgettracker.expensecategory;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
@Builder
@Entity
@Table(name = "user_expense_categories")
class UserExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String categoryName;

}
