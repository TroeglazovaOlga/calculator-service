package io.primeaspect.calculator.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "expression")
data class Expression(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore
        val id: Long? = null,
        val date: LocalDate,
        val expression: String,
        val solution: Double
)