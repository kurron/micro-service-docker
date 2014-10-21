package org.example.shell.shared

import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.hystrix.HystrixCommand
import org.example.shared.resilience.HystrixSettingsBuilder
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate

/**
 * Guards the interaction with RabbitMQ via circuit-breaker..
 */
class RabbitGateway extends HystrixCommand<String> {

    /**
     * The resource being guarded.
     */
    private final RabbitTemplate theTemplate

    /**
     * The queue to post to, via the default exchange.
     */
    private final String theQueue

    /**
     * The AMQP message to send.
     */
    private final Message theMessage

    /**
     * JSON-to-POGO mapper.
     */
    private final ObjectMapper theMapper

    /**
     * Constructor.  Might see about reducing the argument list.
     * @param aTemplate the RabbitMQ communication template to use.
     * @param aQueue the queue to send the message to.
     * @param aMessage the message to send.
     * @param aMapper the POGO-to-JSON converter to use.
     */
    RabbitGateway( RabbitTemplate aTemplate,
                   String aQueue,
                   Message aMessage,
                   ObjectMapper aMapper ) {
        super( HystrixSettingsBuilder.buildUsingDefaults( 'RabbitMQ Guard' ) )
        theTemplate = aTemplate
        theQueue = aQueue
        theMessage = aMessage
        theMapper = aMapper
    }

    @Override
    protected String run() throws Exception {
        def response = theTemplate.sendAndReceive( theQueue, theMessage )
        printJsonPayload( response )
    }

    @Override
    protected String getFallback() {
        'FAILURE: unable to contact the micro-service!'
    }

    private String printJsonPayload( Message response ) {
        def json = theMapper.readValue( response.body, Object )
        theMapper.writerWithDefaultPrettyPrinter().writeValueAsString( json )
    }
}
