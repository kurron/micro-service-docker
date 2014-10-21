package org.example.shared.resilience

import com.netflix.hystrix.HystrixCommand

/**
 * A command the showcases how to fail fast against a faltering integration point.
 */
class ExampleFailFastCommand extends HystrixCommand<String> {

    private final FlickeringResource theResource

    ExampleFailFastCommand( FlickeringResource aRresource ) {
        super( HystrixSettingsBuilder.buildForFailFastCommand( 'fail fast' ) )
        theResource = aRresource
    }

    @Override
    protected String run() throws Exception {
        theResource.fetchResource()
    }
}
