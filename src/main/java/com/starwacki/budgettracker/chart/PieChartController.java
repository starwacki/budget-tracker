package com.starwacki.budgettracker.chart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/piechart")
public class PieChartController {

    private final ChartService chartService;

    @GetMapping("/v1/{username}")
    public ResponseEntity<ChartDTO<String>> getPieChartOfUsernameCategoriesExpenses(@PathVariable String username) {
        return ResponseEntity.ok(chartService.getPieChartOfAllUsernameCategoriesExpenses(username));
    }

    @GetMapping(value = "/v1/{username}",params = "weekDate")
    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserWeekCategoriesExpenses(@PathVariable String username, @RequestParam LocalDate weekDate) {
        return ResponseEntity.ok(chartService.getPieChartOfUserWeekCategoriesExpenses(username,weekDate));
    }

    @GetMapping(value = "/v1/{username}",params = "month")
    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserMonthCategoriesExpenses(@PathVariable String username, @RequestParam int month) {
        return ResponseEntity.ok(chartService.getPieChartOfUserMonthCategoriesExpenses(username,month));
    }

    @GetMapping(value = "/v1/{username}",params = "year")
    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserYearCategoriesExpenses(@PathVariable String username, @RequestParam int year) {
        return ResponseEntity.ok(chartService.getPieChartOfUserYearCategoriesExpenses(username,year));
    }

    @GetMapping(value = "/v1/{username}",params = {"from","to"})
    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserPeriodCategoriesExpenses(@PathVariable String username, @RequestParam LocalDate from, @RequestParam LocalDate to) {
        return ResponseEntity.ok(chartService.getPieChartOfUserPeriodCategoriesExpenses(username,from,to));
    }

}
