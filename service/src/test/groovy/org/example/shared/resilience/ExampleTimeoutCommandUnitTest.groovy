package org.example.shared.resilience

import org.example.shared.BaseUnitTest

/**
 * Unit test of the ExampleTimeoutCommand object.
 */
class ExampleTimeoutCommandUnitTest extends BaseUnitTest {

    def 'exercise happy path'() {
        given: 'a fast resource'
        def resource = new LatentResource( 0 )

        and: 'a valid subject under test'
        def sut = new ExampleTimeoutCommand( resource )

        when: 'the command is executed'
        def results = sut.execute()

        then: 'the resource is returned'
        'Success!' == results
    }

    def 'exercise latent path'() {
        given: 'a slow resource'
        def resource = new LatentResource( 2000 )

        and: 'a valid subject under test'
        def sut = new ExampleTimeoutCommand( resource )

        when: 'the command is executed'
        def results = sut.execute()

        then: 'a timeout occurs and the fallback mechanism is invoked'
        'Resource timed out!' == results
    }
}
