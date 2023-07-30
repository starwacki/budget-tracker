package com.starwacki.budgettracker.chart;

import java.util.HashMap;

record ChartDTO<T>(
        HashMap<T, ChartCategory> expenses
) {}
