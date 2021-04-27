package io.primeaspect.calculator.integration.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class TestKafkaListener {
    @KafkaListener(
        topics = ["test-topic"],
        containerFactory = "testTopicKafkaConsumerFactory",
        groupId = "foo"
    )
    fun onMessage(message: String) {
        println("!!!!!!!!!!!!!!!!!$message!!!!!!!!!!!!!!!!!")
    }

}
