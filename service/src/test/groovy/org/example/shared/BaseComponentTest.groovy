package org.example.shared

import org.example.Application
import org.example.ApplicationProperties
import org.example.echo.EchoDocumentRepository
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.integration.support.json.JsonObjectMapper
import org.springframework.test.context.ContextConfiguration

/**
 * Common base class for all component-level tests. Sometimes knows as integration tests,
 * these tests access the file system, network and other external resources but don't test
 * the entire system end-to-end from a user's perspective like a functional acceptance test
 * do.
 */
@ContextConfiguration( classes = Application, loader = SpringApplicationContextLoader )
class BaseComponentTest extends BaseSpecification {

    /**
     * Application-specific properties.
     */
    @Autowired
    protected ApplicationProperties applicationProperties

    /**
     * Manages interactions with RabbitMQ.
     */
    @Autowired
    protected RabbitOperations rabbitOperations

    /**
     * Manages JSON-to-POGO transformations.
     */
    @Autowired
    protected JsonObjectMapper objectMapper

    /**
     * Manages interactions with the database.
     */
    @Autowired
    protected EchoDocumentRepository echoDocumentRepository
}
