package com.starwacki.budgettracker.chart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/piechart")
public class PieChartController {

    private final ChartService chartService;

    @GetMapping("/{username}")
    public ResponseEntity<ChartDTO<String>> getPieChartOfUsernameCategoriesExpenses(@PathVariable String username) {
        return ResponseEntity.ok(chartService.getPieChartOfAllUsernameCategoriesExpenses(username));
    }

    @GetMapping("/{username}/week={date}")
    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserWeekCategoriesExpenses(@PathVariable String username, @PathVariable LocalDate date) {
        System.out.println(date);
        return ResponseEntity.ok(chartService.getPieChartOfUserWeekCategoriesExpenses(username,date));
    }

    @GetMapping("/{username}/month={month}")
    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserMonthCategoriesExpenses(@PathVariable String username, @PathVariable int month) {
        return ResponseEntity.ok(chartService.getPieChartOfUserMonthCategoriesExpenses(username,month));
    }

    @GetMapping("/{username}/year={year}")
    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserYearCategoriesExpenses(@PathVariable String username, @PathVariable int year) {
        return ResponseEntity.ok(chartService.getPieChartOfUserYearCategoriesExpenses(username,year));
    }

    @GetMapping("/{username}/from={startDate}&to={endDate}")
    public ResponseEntity<ChartDTO<String>>  getPieChartOfUserPeriodCategoriesExpenses(@PathVariable String username, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return ResponseEntity.ok(chartService.getPieChartOfUserPeriodCategoriesExpenses(username,startDate,endDate));
    }

}
