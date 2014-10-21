package org.example.shell.shared

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.shell.ApplicationProperties
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.core.CommandMarker

import java.security.SecureRandom

/**
 * Convenience base class for all shell commands that interact with the micro-service.
 */
@SuppressWarnings( ['AbstractClassWithoutAbstractMethod'] )
abstract class BaseCommand implements CommandMarker {

    /**
     * Random number generator.
     */
    protected static final SecureRandom SECURE_RANDOM = new SecureRandom()

    /**
     * JSON-to-POGO mapper.
     */
    @Autowired
    protected ObjectMapper theMapper

    /**
     * Application-specific properties.
     */
    @Autowired
    protected ApplicationProperties configuration

    /**
     * Handles AMQP communications.
     */
    @Autowired
    protected RabbitTemplate theTemplate

    /**
     * Generate a unique message identifier.
     * @return unique message ID.
     */
    protected static String generateMessageID() {
        UUID.randomUUID().toString()
    }

    /**
     * Generates a timestamp based on the current time and UTC time zone.
     * @return generated time stamp.
     */
    protected static Date generateTimeStamp() {
        Calendar.getInstance( TimeZone.getTimeZone( 'UTC' ) ).time
    }

    /**
     * Generates a random hex string.  Useful for testing.
     * @return generated string.
     */
    protected static String hexString() {
        Integer.toHexString( SECURE_RANDOM.nextInt( Integer.MAX_VALUE ) ).toUpperCase()
    }

    /**
     * Generates a random identifier useful as a correlation ID.
     * @return generated ID.
     */
    protected static byte[] generateCorrelationID() {
        // there is some strange UTF-8 encoding going on here so don't just send randomized bytes
        UUID.randomUUID().toString().bytes
    }

    /**
     * Convenience method for generating messages, ensuring required properties are proplery set.
     * @param payload contents of the message.
     * @param contentType the Mime-Type of the message.
     * @return assembled message.
     */
    protected Message createMessage( byte [] payload,
                                     String contentType ) {
        MessageBuilder.withBody( payload )
                      .setContentType( contentType )
                      .setMessageId( generateMessageID() )
                      .setTimestamp( generateTimeStamp() )
                      .setAppId( configuration.applicationID )
                      .setCorrelationId( generateCorrelationID() )
                      .build()
    }

    /**
     * Converts a Jackson-compatible object into a JSON payload.
     * @param value object to transform.
     * @return byte representation of the JSON payload.
     */
    protected byte[] toJsonBytes( Object value ) {
        theMapper.writeValueAsBytes( value )
    }

    /**
     * Sends a message to the specified destination, wrapping the call in a circuit-breaker.
     * @param message message to send.
     * @param queue destination of the message.
     * @return results of the operation which may be a defaulted error message if there
     * was a problem delivering the message.
     */
    protected String sendMessage( Message message, String queue ) {
        new RabbitGateway( theTemplate, queue, message, theMapper ).execute()
    }
}
