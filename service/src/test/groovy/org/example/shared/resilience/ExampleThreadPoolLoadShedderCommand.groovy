package org.example.shared.resilience

import com.netflix.hystrix.HystrixCommand

/**
 * A command the showcases how to shed load when working with potentially latent integration points.
 */
class ExampleThreadPoolLoadShedderCommand extends HystrixCommand<String> {

    private final LatentResource theResource

    ExampleThreadPoolLoadShedderCommand( LatentResource aResource) {
        super( HystrixSettingsBuilder.buildForThreadPoolLoadShedderCommand( 'thread-pool load shed example' ) )
        theResource = aResource
    }

    @Override
    protected String run() throws Exception {
        theResource.fetchResource()
    }

    @Override
    protected String getFallback() {
        'Fallback triggered!'
    }
}
