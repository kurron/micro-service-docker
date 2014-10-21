package org.example.shared.resilience

import groovy.transform.Canonical

/**
 * An example of a flickering integration point that sometimes works and sometimes doesn't.
 */
@Canonical
class FlickeringResource {
    boolean shouldFail = false

    @SuppressWarnings( 'ThrowRuntimeException' )
    String fetchResource() {
        if ( shouldFail ) {
            throw new RuntimeException( 'I was forced to fail!' )
        }
        'Success!'
    }
}
