package io.primeaspect.calculator.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.kafka.listener.ContainerProperties
import java.net.InetAddress

@ConfigurationProperties(prefix = "spring.kafka")
class KafkaProperties {
    lateinit var defaultTopic: String
    lateinit var bootstrapServers: String
    lateinit var groupId: String
    lateinit var batchSize: Integer
    lateinit var maxInFlightRequestsPerConnection: Integer
    lateinit var autoOffsetReset: String
    lateinit var isolationLevel: String
    lateinit var enableAutoCommit: String
    var acks = "all"
    var ackMode = ContainerProperties.AckMode.RECORD
    var maxPollRecords = 10
    var lingerMs = 100
    var concurrency = 1
    var clientId = InetAddress.getLocalHost().hostName
    var retries = 3
    var retryBackoffMs = 500L
}