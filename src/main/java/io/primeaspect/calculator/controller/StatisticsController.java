package io.primeaspect.calculator.controller;

import io.primeaspect.calculator.dto.response.ExpressionListResponse;
import io.primeaspect.calculator.model.Number;
import io.primeaspect.calculator.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService service;

    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @RequestMapping(value = "/date", params = "date")
    public ExpressionListResponse findAllByDate(@RequestParam String date) {
        return service.findAllByDate(date);
    }

    @RequestMapping(value = "/operation", params = "operation")
    public ExpressionListResponse findAllByOperation(@RequestParam String operation) {
        return service.findAllByOperation(operation);
    }

    @RequestMapping(value = "/count/date", params = "date")
    public int getCountByDate(@RequestParam String date) {
        return service.getCountByDate(date);
    }

    @RequestMapping(value = "/count/operation", params = "operation")
    public int getCountByOperation(@RequestParam String operation) {
        return service.getCountByOperation(operation);
    }

    @GetMapping("/popular")
    public Number getPopularNumber() {
        return service.getPopularNumber();
    }
}
