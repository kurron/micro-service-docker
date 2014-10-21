package org.example.shared.resilience

import org.example.shared.BaseUnitTest

/**
 * Unit test of the ExampleFailSilentCommand object.
 */
class ExampleFailSilentCommandUnitTest extends BaseUnitTest {

    def 'exercise happy path'() {
        given: 'a working resource'
        def resource = new FlickeringResource( false )

        and: 'a valid subject under test'
        def sut = new ExampleFailSilentCommand( resource )

        when: 'the command is executed'
        def results = sut.execute()

        then: 'the resource is returned'
        'Success!' == results
    }

    def 'exercise failing integration scenario'() {
        given: 'a working resource'
        def resource = new FlickeringResource( true )

        and: 'a valid subject under test'
        def sut = new ExampleFailSilentCommand( resource )

        when: 'the command is executed'
        def results = sut.execute()

        then: 'null result is returned'
        !results
    }

}
