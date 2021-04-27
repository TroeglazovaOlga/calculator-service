package io.primeaspect.calculator.configuration

import io.primeaspect.calculator.configuration.properties.KafkaProperties
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate

@Configuration
class KafkaConfiguration(
        private var properties: KafkaProperties
) {
    @Bean
    fun testKafkaTemplate() = KafkaTemplate(defaultKafkaProducerFactory<String, Any>()).apply {
        this.defaultTopic = properties.defaultTopic
    }

    @Bean
    fun testTopicKafkaConsumerFactory() =
            ConcurrentKafkaListenerContainerFactory<String, String>().apply {
                this.consumerFactory = DefaultKafkaConsumerFactory(commonKafkaConsumerConfigs())
                this.containerProperties.ackMode = properties.ackMode
                this.setConcurrency(properties.concurrency)
            }

    private fun <K, V> defaultKafkaProducerFactory(): DefaultKafkaProducerFactory<K, V> =
            DefaultKafkaProducerFactory(commonKafkaProducerConfigs())

    private fun commonKafkaProducerConfigs() = mapOf(
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to properties.bootstrapServers,
            ProducerConfig.ACKS_CONFIG to properties.acks,
            ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION to properties.maxInFlightRequestsPerConnection,
            ProducerConfig.BATCH_SIZE_CONFIG to properties.batchSize,
            ProducerConfig.LINGER_MS_CONFIG to properties.lingerMs,
            ProducerConfig.CLIENT_ID_CONFIG to properties.clientId,

            // retry
            ProducerConfig.RETRIES_CONFIG to properties.retries,
            ProducerConfig.RETRY_BACKOFF_MS_CONFIG to properties.retryBackoffMs
    )

    private fun commonKafkaConsumerConfigs(): MutableMap<String, Any> = mutableMapOf(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to properties.bootstrapServers,
            ConsumerConfig.CLIENT_ID_CONFIG to properties.clientId,
            ConsumerConfig.GROUP_ID_CONFIG to properties.groupId,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to properties.maxPollRecords,

            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to properties.enableAutoCommit,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to properties.autoOffsetReset,
            ConsumerConfig.ISOLATION_LEVEL_CONFIG to properties.isolationLevel
    )

}