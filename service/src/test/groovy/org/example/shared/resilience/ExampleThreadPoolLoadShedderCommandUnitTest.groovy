package org.example.shared.resilience

import java.util.concurrent.Future
import org.example.shared.BaseUnitTest
import spock.lang.Ignore

/**
 * Unit test of the ExampleThreadPoolLoadShedderCommand object.
 */
@Ignore( 'The unpredictable nature of multi-threaded tests on different hardware, this test flickers.' )
class ExampleThreadPoolLoadShedderCommandUnitTest extends BaseUnitTest {

    def 'exercise too much load on the resource'() {
        List<Future<String>> futures = []

        given: 'a slow resource'
        def resource = new LatentResource( 750 )

        and: 'all available threads are used up'
        10.times {
            futures <<  new ExampleThreadPoolLoadShedderCommand( resource ).queue()
        }

        when: 'the command is executed'
        def sut = new ExampleThreadPoolLoadShedderCommand( resource )
        def results = sut.execute()

        then: 'the operation is denied and the fallback mechanism is invoked'
        'Fallback triggered!' == results

        and: 'all others succeed'
        futures.each {
            assert 'Success!' == it.get()
        }
    }
}
