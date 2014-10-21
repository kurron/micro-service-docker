package org.example.shared.resilience

import java.util.concurrent.Callable

/**
 * Wrapper around the command so it can be used in the concurrency constructs.
 */
class SemaphoreCommandInvocation implements Callable<String> {
    private final LatentResource theResource

    SemaphoreCommandInvocation( LatentResource aResource ) {
        theResource = aResource
    }

    @Override
    String call() throws Exception {
        new ExampleSemaphoreLoadShedderCommand( theResource ).execute()
    }
}
