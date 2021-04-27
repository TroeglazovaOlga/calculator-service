package io.primeaspect.calculator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication(
    exclude = [KafkaAutoConfiguration::class]
)
@ConfigurationPropertiesScan("io.primeaspect.calculator")
@EnableKafka
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
