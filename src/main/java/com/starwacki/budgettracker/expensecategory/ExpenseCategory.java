package com.starwacki.budgettracker.expensecategory;

enum ExpenseCategory {

    FOOD("Food"),
    HOME("Home"),
    ANIMALS("Animals"),
    CAR("Car"),
    CLOTHES("Clothes"),
    HOBBY("Hobby"),
    DEBTS("Debts"),
    EDUCATION("Education"),
    ENTERTAINMENT("Entertainment"),
    OTHER("Other");

    private String categoryName;

    ExpenseCategory(String name) {
        this.categoryName = name;
    }

    String getCategoryName() {
        return this.categoryName;
    }

}
