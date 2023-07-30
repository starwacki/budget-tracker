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
@RequestMapping("/graph")
public class ChartController {

    private final ChartService chartService;

    @GetMapping("/{username}")
    public ResponseEntity<GraphOfExpenses> getGraphOfAllUsernameExpenses(@PathVariable String username) {
        return ResponseEntity.ok(chartService.createGraphOfUserExpenses(username));
    }

    @GetMapping("/{username}/category={expenseCategory}")
    public ResponseEntity<GraphOfExpenses>  getGraphOfAllUsernameExpensesWithExpenseCategory(@PathVariable String username, @PathVariable String expenseCategory) {
        return ResponseEntity.ok(chartService.createGraphOfUserExpensesWhenExpenseCategory(username,expenseCategory));
    }

    @GetMapping("/{username}/date={date}")
    public ResponseEntity<GraphOfExpenses> getGraphOfAllUsernameExpensesWithDate(@PathVariable String username, @PathVariable LocalDate date) {
        return ResponseEntity.ok(chartService.createGraphOfUserExpensesWhenDate(username,date));
    }

    @GetMapping("/{username}/month={monthOrder}")
    public ResponseEntity<GraphOfExpenses> getGraphOfAllUsernameExpensesWithMonth(@PathVariable String username, @PathVariable int monthOrder) {
        return ResponseEntity.ok(chartService.createGraphOfUserExpensesWhenMonth(username,monthOrder));
    }

    @GetMapping("/{username}/year={year}")
    public ResponseEntity<GraphOfExpenses> findAllYearExpenses(@PathVariable String username, @PathVariable int year) {
        return ResponseEntity.ok(chartService.createGraphOfUserExpensesWhenYear(username,year));
    }

    @GetMapping("/{username}/from={startDate}&to={endDate}")
    public ResponseEntity<GraphOfExpenses> findAllPeriodExpenses(@PathVariable String username, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return ResponseEntity.ok(chartService.createGraphOfUserExpensesWhenPeriod(username,startDate,endDate));
    }

}
