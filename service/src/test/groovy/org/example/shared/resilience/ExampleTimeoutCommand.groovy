package org.example.shared.resilience

import com.netflix.hystrix.HystrixCommand

/**
 * A command the showcases how to timeout potentially latent integration points.
 */
class ExampleTimeoutCommand extends HystrixCommand<String> {

    private final LatentResource theResource

    ExampleTimeoutCommand( LatentResource aResource) {
        super( HystrixSettingsBuilder.buildForTimeoutCommand( 'timeout example', 100 ) )
        theResource = aResource
    }

    @Override
    protected String run() throws Exception {
        theResource.fetchResource()
    }

    @Override
    protected String getFallback() {
        'Resource timed out!'
    }
}
