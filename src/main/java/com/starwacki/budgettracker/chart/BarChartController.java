package com.starwacki.budgettracker.chart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

@RestController
@RequiredArgsConstructor
@RequestMapping("/barchart")
public class BarChartController {

    private final ChartService chartService;

    @GetMapping(value = "/v1/{username}/week-chart/{weekDate}")
    public ResponseEntity<ChartDTO<DayOfWeek>> getWeekBarChart(@PathVariable String username, @PathVariable LocalDate weekDate) {
        return ResponseEntity.ok(chartService.getBarChartOfUserExpensesInWeek(username,weekDate));
    }

    @GetMapping(value = "/v1/{username}/year-chart/{year}")
    public ResponseEntity<ChartDTO<Month>> getYearBarChart(@PathVariable String username, @PathVariable int year) {
        return ResponseEntity.ok(chartService.getBarChartOfUserExpensesInYear(username,year));
    }

}
