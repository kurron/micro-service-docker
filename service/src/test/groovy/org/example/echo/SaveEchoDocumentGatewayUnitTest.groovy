package org.example.echo

import com.netflix.hystrix.exception.HystrixRuntimeException
import org.example.shared.BaseUnitTest

/**
 * Unit level test of the SaveEchoDocumentGateway object.  No matter what I do, JaCoCo is not happy with the coverage
 * so I had to add it to the ignore file.  I think it is the complexity of the Hystrix command it inherits from that
 * is causing the issue.
 */
@SuppressWarnings( 'ThrowRuntimeException' )
class SaveEchoDocumentGatewayUnitTest extends BaseUnitTest {

    private static final String FAILED = 'Forced to fail!'

    def 'verify normal operation'() {
        given: 'a document to save'
        def toSave = new EchoDocument( message: randomHexString() )

        and: 'a test double'
        def repository = Mock( EchoDocumentRepository )
        1 * repository.save( toSave ) >> { EchoDocument document ->
            document.id = randomHexString()
            document.version = randomPositiveInteger( 100 )
            document
        }

        and: 'a subject under test'
        def sut = new SaveEchoDocumentGateway( repository, toSave )

        when: 'message storage is requested'
        EchoDocument saved = sut.execute()

        then: 'the document is saved'
        saved.id
        saved.version
        saved.message == toSave.message
    }

    def 'verify failing resource'() {
        given: 'a document to save'
        def toSave = new EchoDocument( message: randomHexString() )

        and: 'a test double'
        def repository = Stub( EchoDocumentRepository )
        repository.save( toSave ) >> { throw new RuntimeException( FAILED ) }

        and: 'a subject under test'
        def sut = new SaveEchoDocumentGateway( repository, toSave )

        when: 'message storage is requested'
        sut.execute()

        then: 'errors are returned'
        def thrown = thrown( HystrixRuntimeException )
        FAILED == thrown.cause.message
    }
}
