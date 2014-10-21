package org.example.echo

import org.example.shared.BaseUnitTest
import org.springframework.integration.support.json.Jackson2JsonObjectMapper

/**
 * Unit level test of the DocumentWriter object.
 */
class DocumentWriterUnitTest extends BaseUnitTest {

    private static final MAPPER = new Jackson2JsonObjectMapper()

    def 'verify transform operation'() {
        given: 'subject under test'
        def repository = Stub( EchoDocumentRepository )
        repository.save( _ ) >> { EchoDocument document ->
            document.id = randomHexString()
            document
        }
        def sut = new DocumentWriter( theObjectMapper: MAPPER, theRepository: repository )

        and: 'a valid message'
        def request = new EchoRequest( message: randomHexString() )

        when: 'message storage is requested'
        def output = sut.storeDocument( request )

        then: 'the document id is returned'
        def echoResponse = MAPPER.fromJson( output.payload, EchoResponse )
        echoResponse.id
    }
}
