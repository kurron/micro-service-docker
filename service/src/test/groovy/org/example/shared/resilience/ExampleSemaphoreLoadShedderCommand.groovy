package org.example.shared.resilience

import com.netflix.hystrix.HystrixCommand

/**
 * A command the showcases how to shed load when working with potentially latent integration points.
 */
class ExampleSemaphoreLoadShedderCommand extends HystrixCommand<String> {

    private final LatentResource theResource

    ExampleSemaphoreLoadShedderCommand( LatentResource aResource) {
        super( HystrixSettingsBuilder.buildForSemaphoreLoadShedderCommand( 'semaphore load shed example' ) )
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
