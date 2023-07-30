package com.starwacki.budgettracker.chart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

@RestController
@RequiredArgsConstructor
@RequestMapping("/barchart")
public class BarChartController {

    private final ChartService chartService;

    @GetMapping("/{username}/week={date}")
    public ResponseEntity<ChartDTO<DayOfWeek>> getWeeklyBarChart(@PathVariable String username, @PathVariable LocalDate date) {
        return ResponseEntity.ok(chartService.getBarChartOfUserExpensesInWeek(username,date));
    }

    @GetMapping("/{username}/year={year}")
    public ResponseEntity<ChartDTO<Month>> getYearlyBarChart(@PathVariable String username,@PathVariable int year) {
        return ResponseEntity.ok(chartService.getBarChartOfUserExpensesInYear(username,year));
    }

}
