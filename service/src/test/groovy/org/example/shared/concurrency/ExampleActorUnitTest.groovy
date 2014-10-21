package org.example.shared.concurrency

import groovy.util.logging.Slf4j
import groovyx.gpars.actor.Actors
import org.example.shared.BaseUnitTest

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Unit test of actor-based concurrency object.
 */
@Slf4j
class ExampleActorUnitTest extends BaseUnitTest {

    def 'exercise one-way message passing'() {
        given: 'a couple of subjects under test'
        def depp = new HollywoodActor( name: 'Johnny Depp' )
        def hanks = new HollywoodActor( name: 'Tom Hanks' )

        and: 'the subjects are activated'
        [depp, hanks]*.start()

        when: 'messages are sent'
        depp.send( 'Sparrow')
        depp.send( 'Wonka')
        depp.send( 'Scissorhands')
        depp.send( 'Ceaser')

        hanks.send( 'Lovell' )

        and: 'we wait for a second'
        [depp, hanks]*.join( 1, TimeUnit.SECONDS )

        then: 'messages are printed to the screen'
        true
    }

    def 'exercise non-blocking multi-party message passing'() {

        given: 'a subject subject under test'
        def fortuneTeller = Actors.actor {
            loop {
                react { name ->
                    sender.send( "${name}, you have a very bright future!" )
                }
            }
        }

        when: 'multiple non-blocking messages are sent'
        def latch = new CountDownLatch( 2 )
        fortuneTeller.sendAndContinue( 'Bob' ) { log.debug it ; latch.countDown() }
        fortuneTeller.sendAndContinue( 'Fred' ) { log.debug it; latch.countDown() }

        then: 'messages are printed to the screen'
        if ( latch.await( 1, TimeUnit.SECONDS ) ) {
            log.debug 'Bob and Fred are happy campers.'
        }
        else {
            log.debug 'Fortune teller did not response in time'
        }
    }

    def 'exercise discreet message handling'() {

        given: 'a subject subject under test'
        def trader = new Trader().start()

        when: 'a symbol lookup message is sent in the background'
        trader.sendAndContinue( new Lookup( 'XYZ' ) ) {
            log.debug "The price of XYZ stock is ${it}"
        }

        and: 'a buy message is sent'
        trader << new Buy( 'XYZ', 200 )

        then: 'messages are printed to the screen'
        trader.join( 1, TimeUnit.SECONDS )
    }
}
