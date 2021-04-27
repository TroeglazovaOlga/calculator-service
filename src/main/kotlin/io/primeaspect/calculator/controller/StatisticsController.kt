package io.primeaspect.calculator.controller

import io.primeaspect.calculator.service.StatisticsService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/statistics")
class StatisticsController(private var service: StatisticsService) {
    @GetMapping(value = ["/date"], params = ["date"])
    fun findAllByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            date: LocalDate
    ) = service.findAllByDate(date)

    @GetMapping(value = ["/operation"], params = ["operation"])
    fun findAllByOperation(
            @RequestParam
            operation: String
    ) = service.findAllByOperation(operation)

    @GetMapping(value = ["/count/date"], params = ["date"])
    fun getCountByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            date: LocalDate
    ) = service.getCountByDate(date)

    @GetMapping(value = ["/count/operation"], params = ["operation"])
    fun getCountByOperation(
            @RequestParam
            operation: String
    ) = service.getCountByOperation(operation)

    @GetMapping("/popular")
    fun getPopularNumber() = service.getPopularNumber()

    @GetMapping(value = ["/popular/date"], params = ["date"])
    fun getPopularNumberByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            date: LocalDate
    ) = service.getPopularNumberByDate(date)
}
