package io.primeaspect.calculator.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "number")
data class Number(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore
        val id: Long? = null,
        val value: Double,
        val date: LocalDate,
        var count: Int? = 1
)