package io.primeaspect.calculator.integration.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class TestKafkaProducer(
        private val kafkaTemplate: KafkaTemplate<String, Any>,
        private val mapper: ObjectMapper
) {
    fun sendMessage(message: String) {
        kafkaTemplate.sendDefault(mapper.writeValueAsString(message))
    }
}