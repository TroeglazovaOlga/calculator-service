package io.primeaspect.calculator.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.oxm.jaxb.Jaxb2Marshaller

@Configuration
class JaxbConfiguration {
    @Bean
    fun statisticsResponseMarshaller(): Jaxb2Marshaller = Jaxb2Marshaller().apply {
        setPackagesToScan("io.primeaspect.calculator")
    }
}