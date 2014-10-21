package org.example.echo

import org.example.shared.BaseComponentTest
import org.springframework.amqp.core.MessageBuilder

/**
 * Component test of the 'echo' services.
 */
class EchoServiceComponentTest extends BaseComponentTest {

    def 'verify save operation'() {
        given: 'subject under test'
        assert echoDocumentRepository

        and: 'a valid request'
        def request = new EchoRequest( UUID.randomUUID().toString() )

        when: 'the message is sent'
        def message = MessageBuilder.withBody( objectMapper.toJson( request ).bytes )
                                    .setContentType( 'application/json;type=echo-request' )
                                    .setMessageId( UUID.randomUUID().toString() )
                                    .setTimestamp( Calendar.getInstance( TimeZone.getTimeZone( 'UTC' ) ).time )
                                    .setAppId( 'send-test' )
                                    .setCorrelationId( UUID.randomUUID().toString().bytes )
                                    .build()
        // use the default exchange and the routing key is the queue name
        def response = rabbitOperations.sendAndReceive( applicationProperties.queue, message )
        EchoResponse echoResponse = objectMapper.fromJson( response.body, EchoResponse )

        then: 'the document can be found in the database'
        EchoDocument found = echoDocumentRepository.findOne( echoResponse.id )

        and: 'the document matches the original'
        found.message == request.message
    }
}
