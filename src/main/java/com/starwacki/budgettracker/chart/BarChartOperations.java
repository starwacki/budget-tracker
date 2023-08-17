package com.starwacki.budgettracker.chart;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

@RestController
@Tag(name = "bar-chart-operations")
interface BarChartOperations {

    @GetMapping(value = "/{username}/week-chart/{weekDate}")
    ResponseEntity<ChartDTO<DayOfWeek>> getWeekBarChart(@PathVariable String username, @PathVariable LocalDate weekDate);

    @GetMapping(value = "/{username}/year-chart/{year}")
    ResponseEntity<ChartDTO<Month>> getYearBarChart(@PathVariable String username, @PathVariable int year);
}
