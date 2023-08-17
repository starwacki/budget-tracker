package com.starwacki.budgettracker.chart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

@RestController
@RequiredArgsConstructor
@RequestMapping("/barchart/v1")
public class BarChartController implements BarChartOperations {

    private final ChartService chartService;

    @Override
    public ResponseEntity<ChartDTO<DayOfWeek>> getWeekBarChart(String username , LocalDate weekDate) {
        return ResponseEntity.ok(chartService.getBarChartOfUserExpensesInWeek(username,weekDate));
    }

    @Override
    public ResponseEntity<ChartDTO<Month>> getYearBarChart(String username, int year) {
        return ResponseEntity.ok(chartService.getBarChartOfUserExpensesInYear(username,year));
    }

}
