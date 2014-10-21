package org.example.shared.resilience

import groovy.transform.Canonical

/**
 * An example of a slow running integration point.
 */
@Canonical
class LatentResource {
    long latency = 1000

    @SuppressWarnings( 'EmptyCatchBlock' )
    String fetchResource() {
        if ( latency ) {
            try {
                Thread.currentThread().sleep( latency )
            } catch ( InterruptedException e ) {
                // nothing important to do
            }
        }
        'Success!'
    }
}
