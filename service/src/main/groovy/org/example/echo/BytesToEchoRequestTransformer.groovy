package org.example.echo

import static org.example.shared.feedback.CustomFeedbackContext.PAYLOAD_TRANSFORMATION

import org.example.shared.feedback.BaseFeedbackAware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.support.MessageBuilder
import org.springframework.integration.support.json.JsonObjectMapper
import org.springframework.integration.transformer.Transformer
import org.springframework.messaging.Message

/**
 * Handles converting the raw message payload into a POGO.
 */

class BytesToEchoRequestTransformer extends BaseFeedbackAware implements Transformer {

    /**
     * Manages JSON-to-POGO transformations.
     */
    @Autowired
    JsonObjectMapper objectMapper

    @Override
    Message<?> transform( Message<?> message) {
        feedbackProvider.sendFeedback( PAYLOAD_TRANSFORMATION )
        // a more robust implementation would verify the content-type before proceeding
        MessageBuilder.withPayload( objectMapper.fromJson( message.payload, EchoRequest ) ).copyHeaders( message.headers ).build()
    }
}
