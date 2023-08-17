package com.starwacki.budgettracker.chart;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/piechart/v1")
public class PieChartController implements PieChartOperations {

    private final ChartService chartService;

    public ResponseEntity<ChartDTO<String>> getPieChartOfUsernameCategoriesExpenses(String username) {
        return ResponseEntity.ok(chartService.getPieChartOfAllUsernameCategoriesExpenses(username));
    }

    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserWeekCategoriesExpenses(String username, LocalDate weekDate) {
        return ResponseEntity.ok(chartService.getPieChartOfUserWeekCategoriesExpenses(username,weekDate));
    }

    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserMonthCategoriesExpenses(String username, int month) {
        return ResponseEntity.ok(chartService.getPieChartOfUserMonthCategoriesExpenses(username,month));
    }

    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserYearCategoriesExpenses(String username, int year) {
        return ResponseEntity.ok(chartService.getPieChartOfUserYearCategoriesExpenses(username,year));
    }

    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserPeriodCategoriesExpenses(String username, LocalDate from, LocalDate to) {
        return ResponseEntity.ok(chartService.getPieChartOfUserPeriodCategoriesExpenses(username,from,to));
    }

}
