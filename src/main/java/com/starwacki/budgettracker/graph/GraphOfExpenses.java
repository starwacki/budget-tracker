package com.starwacki.budgettracker.graph;

import lombok.*;

import java.util.HashMap;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(value = AccessLevel.PACKAGE)
@ToString
class GraphOfExpenses {

    private HashMap<String, GraphCategory> expenses;

}
