package org.example.shared

import org.example.Application
import org.example.ApplicationProperties
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.integration.support.json.JsonObjectMapper
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.RestOperations

/**
 * Convenience class for step definitions.
 */
@IntegrationTest
@ContextConfiguration( classes = Application, loader = SpringApplicationContextLoader )
class BaseStepDefinition {

    /**
     * Application-specific properties.
     */
    @Autowired
    ApplicationProperties properties

    /**
     * Manages interactions with RabbitMQ.
     */
    @Autowired
    RabbitOperations rabbitOperations

    /**
     * Manages JSON-to-POGO transformations.
     */
    @Autowired
    JsonObjectMapper objectMapper

    /**
     * Manages interactions with HTTP services.
     */
    @Autowired
    RestOperations restOperations
}
