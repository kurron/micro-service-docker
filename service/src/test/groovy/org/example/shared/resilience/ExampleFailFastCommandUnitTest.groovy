package org.example.shared.resilience

import com.netflix.hystrix.exception.HystrixRuntimeException
import org.example.shared.BaseUnitTest

/**
 * Unit test of the ExampleFailFastCommand object.
 */
class ExampleFailFastCommandUnitTest extends BaseUnitTest {

    def 'exercise happy path'() {
        given: 'a working resource'
        def resource = new FlickeringResource( false )

        and: 'a valid subject under test'
        def sut = new ExampleFailFastCommand( resource )

        when: 'the command is executed'
        def results = sut.execute()

        then: 'the resource is returned'
        'Success!' == results
    }

    def 'exercise failing integration scenario'() {
        given: 'a working resource'
        def resource = new FlickeringResource( true )

        and: 'a valid subject under test'
        def sut = new ExampleFailFastCommand( resource )

        when: 'the command is executed'
        sut.execute()

        then: 'errors are returned'
        def thrown = thrown( HystrixRuntimeException )
        'I was forced to fail!' == thrown.cause.message
    }

}
