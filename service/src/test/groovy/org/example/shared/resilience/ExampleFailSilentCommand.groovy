package org.example.shared.resilience

import com.netflix.hystrix.HystrixCommand

/**
 * A command the showcases how to fail silently against a faltering integration point.
 */
class ExampleFailSilentCommand extends HystrixCommand<String> {

    private final FlickeringResource theResource

    ExampleFailSilentCommand( FlickeringResource aRresource ) {
        super( HystrixSettingsBuilder.buildForFailFastCommand( 'fail fast' ) )
        theResource = aRresource
    }

    @Override
    protected String run() throws Exception {
        theResource.fetchResource()
    }

    @Override
    protected String getFallback() {
        null // tells Hystrix to be silent
    }
}
