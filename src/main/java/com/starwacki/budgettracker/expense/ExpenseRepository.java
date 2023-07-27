package com.starwacki.budgettracker.expense;

import org.springframework.data.jpa.repository.JpaRepository;

interface ExpenseRepository extends JpaRepository<Expense,Long> {

}
