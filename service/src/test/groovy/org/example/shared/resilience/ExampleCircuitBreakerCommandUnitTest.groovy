package org.example.shared.resilience

import com.netflix.hystrix.HystrixCircuitBreaker
import org.example.shared.BaseUnitTest

/**
 * Unit test of the ExampleTimeoutCommand object.
 */
class ExampleCircuitBreakerCommandUnitTest extends BaseUnitTest {

    def 'exercise circuit closed'() {
        given: 'a working resource'
        def resource = new FlickeringResource( false )

        and: 'a valid subject under test'
        def sut = new ExampleCircuitBreakerCommand( 'closed breaker', resource )

        when: 'the command is executed'
        def results = sut.execute()

        then: 'the resource is returned'
        'Success!' == results

        and: 'the breaker remains in a closed state'
        def breaker = HystrixCircuitBreaker.Factory.getInstance( sut.commandKey )
        breaker.allowRequest()
    }

    def 'exercise circuit open'() {
        given: 'a working resource'
        def resource = new FlickeringResource( false )

        and: 'a valid subject under test'
        def sut = new ExampleCircuitBreakerCommand( 'open breaker', resource, true )

        when: 'the command is executed'
        def results = sut.execute()

        then: 'a fallback resource is returned'
        'Resource unavailable!' == results

        and: 'the breaker remains in an open state'
        def breaker = HystrixCircuitBreaker.Factory.getInstance( sut.commandKey )
        !breaker.allowRequest()
    }

}
