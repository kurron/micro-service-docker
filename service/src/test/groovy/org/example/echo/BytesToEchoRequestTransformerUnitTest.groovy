package org.example.echo

import org.example.shared.BaseUnitTest
import org.springframework.integration.support.json.Jackson2JsonObjectMapper
import org.springframework.messaging.support.MessageBuilder

/**
 * Unit level test of the BytesToEchoRequestTransformer object.
 */
class BytesToEchoRequestTransformerUnitTest extends BaseUnitTest {

    private static final MAPPER = new Jackson2JsonObjectMapper()

    def 'verify transform operation'() {
        given: 'subject under test'
        def sut = new BytesToEchoRequestTransformer( objectMapper: MAPPER )

        and: 'a valid message'
        def data = new EchoRequest( randomHexString() )
        def input = MessageBuilder.withPayload( MAPPER.toJson( data ).bytes ).build()

        when: 'the transformation is applied'
        def output = sut.transform( input )

        then: 'the message payload is properly converted'
        output.payload == data
    }
}
