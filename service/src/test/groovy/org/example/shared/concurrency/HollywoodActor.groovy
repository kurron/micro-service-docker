package org.example.shared.concurrency

import groovy.transform.Immutable
import groovy.util.logging.Slf4j
import groovyx.gpars.actor.DefaultActor

/**
 * Example of isolated mutability using actors.
 */
@Immutable
@Slf4j
class HollywoodActor extends DefaultActor {
    String name

    @Override
    protected void act() {
        def terminationLogic = {
            log.debug "${name} is done acting"
        }

        loop( 3, terminationLogic ) {
            react { role ->
                log.debug "${name} playing the ${role}"
                log.debug "${name} runs in ${Thread.currentThread()}"
            }
        }
    }
}
