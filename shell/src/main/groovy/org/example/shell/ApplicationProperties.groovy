package org.example.shell

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Application specific properties.
 */
@SuppressWarnings( ['GStringExpressionWithinString'] )
@Component
class ApplicationProperties {

    /**
     * Default value of string properties.
     */
    private static final String UNSET = 'unset'

    /**
     * The name of the AMQP queue where messages go to.
     */
    @Value( '${queue.name}' )
    String queue = UNSET

    /**
     * How the shell should identify itself to the service.
     */
    @Value( '${application.id}' )
    String applicationID = UNSET

    /**
     * Host name or ip address of the RabbitMQ server.
     */
    @Value( '${rabbitmq.host}' )
    String rabbitHost = UNSET

    /**
     * Port of the RabbitMQ server.
     */
    @Value( '${rabbitmq.port}' )
    int rabbitPort = Integer.MIN_VALUE

    /**
     * Virtual host to use.
     */
    @Value( '${rabbitmq.virtualHost}' )
    String rabbitVirtualHost = UNSET

    /**
     * Username to use when communicating with RabbitMQ.
     */
    @Value( '${rabbitmq.username}' )
    String rabbitUsername = UNSET

    /**
     * Password to use when communicating with RabbitMQ.
     */
    @Value( '${rabbitmq.password}' )
    String rabbitPassword = UNSET

}
