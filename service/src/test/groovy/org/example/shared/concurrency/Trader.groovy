package org.example.shared.concurrency

import groovy.transform.Immutable
import groovy.util.logging.Slf4j
import groovyx.gpars.actor.DynamicDispatchActor

import java.security.SecureRandom

/**
 * A concurrent stock trader.
 */
@Immutable
@Slf4j
class Trader extends DynamicDispatchActor {

    /**
     * Random data generator.
     */
    private static final SecureRandom RANDOM = new SecureRandom()

    void onMessage( Buy message ) {
        log.debug "Buying ${message.quantity} shares of ${message.ticker}"
    }

    @SuppressWarnings( 'UnusedMethodParameter' )
    void onMessage( Lookup message ) {
        sender.send( RANDOM.nextInt( Integer.MAX_VALUE ) )
    }
}
