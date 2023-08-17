package com.starwacki.budgettracker.chart;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@Tag(name = "pie-chart-operations")
interface PieChartOperations {

    @GetMapping("/{username}")
    ResponseEntity<ChartDTO<String>> getPieChartOfUsernameCategoriesExpenses(@PathVariable String username);

    @GetMapping(value = "/{username}/week-chart/{weekDate}")
    ResponseEntity<ChartDTO<String>>  getPieChartOfUserWeekCategoriesExpenses(@PathVariable String username, @PathVariable LocalDate weekDate);

    @GetMapping(value = "/{username}/month-chart/{month}")
    ResponseEntity<ChartDTO<String>>  getPieChartOfUserMonthCategoriesExpenses(@PathVariable String username, @PathVariable int month);

    @GetMapping(value = "/{username}/year-chart/{year}")
    ResponseEntity<ChartDTO<String>>  getPieChartOfUserYearCategoriesExpenses(@PathVariable String username, @PathVariable int year);

    @GetMapping(value = "/{username}/period-chart",params = {"from","to"})
    ResponseEntity<ChartDTO<String>>  getPieChartOfUserPeriodCategoriesExpenses(@PathVariable String username, @RequestParam LocalDate from, @RequestParam LocalDate to);
}
