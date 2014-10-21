package org.example.echo

import org.example.shared.BaseComponentTest

/**
 * Component test of the EchoDocumentRepository object.
 */
class EchoDocumentRepositoryComponentTest extends BaseComponentTest {

    def 'exercise save operation'() {
        given: 'subject under test'
        assert echoDocumentRepository

        and: 'populated document'
        def document = new EchoDocument( datetime: 0, message: randomHexString() )

        when: 'save is called'
        EchoDocument saved = echoDocumentRepository.save( document )

        then: 'document can be found in the database'
        EchoDocument found = echoDocumentRepository.findOne( saved.id )

        and: 'the document matches the original'
        found == saved
    }
}
