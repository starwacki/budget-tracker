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

    @GetMapping(value = "/v1/{username}",params = "weekDate")
    public ResponseEntity<ChartDTO<DayOfWeek>> getDailyBarChart(@PathVariable String username, @RequestParam LocalDate weekDate) {
        return ResponseEntity.ok(chartService.getBarChartOfUserExpensesInWeek(username,weekDate));
    }

    @GetMapping(value = "/v1/{username}",params = "year")
    public ResponseEntity<ChartDTO<Month>> getMonthlyBarChart(@PathVariable String username, @RequestParam int year) {
        return ResponseEntity.ok(chartService.getBarChartOfUserExpensesInYear(username,year));
    }

}
