package org.example.echo

import static org.example.shared.feedback.CustomFeedbackContext.DOCUMENT_STORAGE

import org.example.shared.feedback.BaseFeedbackAware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.support.MessageBuilder
import org.springframework.integration.support.json.JsonObjectMapper
import org.springframework.messaging.Message

/**
 * Handles saving an echo request to the database.
 */
class DocumentWriter extends BaseFeedbackAware {

    /**
     * Manages interactions with the database.
     */
    @Autowired
    private EchoDocumentRepository theRepository

    /**
     * Manages JSON-to-POGO transformations.
     */
    @Autowired
    JsonObjectMapper theObjectMapper

    /**
     * Store the data into the database and returns the primary key of the document.
     * @param request data to store.
     * @return a JSON response containing the newly created document's primary key.
     */
    @ServiceActivator
    Message<byte[]> storeDocument( EchoRequest request ) {
        feedbackProvider.sendFeedback( DOCUMENT_STORAGE, request.message )

        def toSave = new EchoDocument( message: request.message, datetime: System.currentTimeMillis() )
        def gateway = new SaveEchoDocumentGateway( theRepository, toSave )
        EchoDocument saved = gateway.run()
        def response = new EchoResponse( saved.id )
        MessageBuilder.withPayload( theObjectMapper.toJson( response ).bytes )
                      .setHeaderIfAbsent( 'contentType', EchoResponse.CONTENT_TYPE ).build()
    }
}
