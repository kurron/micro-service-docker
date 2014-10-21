package org.example.shared.resilience;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * This class exists solely because Groovy could not handle the mixture of Hystrix's static inner classes.
 * Dropping down to Java fixes the problem.  These setting combinations should only be considered examples of what
 * can be done with Hystrix.  You can combine all sorts of settings including:
 * <ul>
 *     <li>isolation strategy (defaults to thread pool)</li>
 *     <li>timeouts (defaults to 1000ms)</li>
 *     <li>number of concurrent requests (defaults to 10)</li>
 *     <li>circuit breaker error threshold percentages (defaults to 50%)</li>
 *     <li>how long to wait before retrying an open breaker (defaults to 5000ms)</li>
 *     <li>thread pool size (defaults to 10)</li>
 *     <li>maximum queue size (defaults to -1)</li>
 *     <li>queue rejection threshold (defaults to 5)</li>
 * </ul>
 */
public class HystrixSettingsBuilder {

    /**
     * Not intended to be instantiated.
     */
    private HystrixSettingsBuilder() {}

    /**
     * Builds the necessary Hystrix configuration instance.
     * @param group the group key to associate the command to.
     * @param command the command key to associate the command to.
     * @param timeout the duration, in milliseconds, to wait for the command to complete before cancelling it and timing out.
     * @return properly constructed Hystrix settings.
     */
    public static HystrixCommand.Setter build( final String group, final String command, final int timeout ) {
        final HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey( group );
        final HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey( command );
        final HystrixThreadPoolKey poolKey = HystrixThreadPoolKey.Factory.asKey( command );
        final HystrixCommandProperties.Setter defaults = HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds( timeout );
        return HystrixCommand.Setter.withGroupKey( groupKey ).andCommandKey( commandKey).andThreadPoolKey( poolKey ).andCommandPropertiesDefaults( defaults );
    }

    /**
     * Builds the necessary Hystrix configuration instance using mostly defaulted values.
     * @param command the command key to associate the command to.
     * @return properly constructed Hystrix settings.
     */
    public static HystrixCommand.Setter buildUsingDefaults( final String command ) {
        final HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey( "example" );
        final HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey( command );
        final HystrixThreadPoolKey poolKey = HystrixThreadPoolKey.Factory.asKey( command );
        final HystrixCommandProperties.Setter defaults = HystrixCommandProperties.Setter();
        return HystrixCommand.Setter.withGroupKey( groupKey ).andCommandKey( commandKey).andThreadPoolKey( poolKey ).andCommandPropertiesDefaults( defaults );
    }

    /**
     * Builds the necessary Hystrix configuration instance suitable for timeout prevention.
     * @param groupKey the group key to associate the command to.
     * @param timeout the duration, in milliseconds, to wait for the command to complete before cancelling it and timing out.
     * @return properly constructed Hystrix settings.
     */
    public static HystrixCommand.Setter buildForTimeoutCommand( final String groupKey, final int timeout) {
        return HystrixCommand.Setter.withGroupKey( HystrixCommandGroupKey.Factory.asKey( groupKey ) )
                             .andCommandPropertiesDefaults( HystrixCommandProperties.Setter()
                             .withExecutionIsolationThreadTimeoutInMilliseconds( timeout ) );

    }

    /**
     * Builds the necessary Hystrix configuration instance suitable for circuit-breaker semantics.
     * @param groupKey the group key to associate the command to.
     * @param commandKey the key to associate the command to.
     * @param forceOpen set to true if you want the breaker to be initially set to the open position.
     * @return properly constructed Hystrix settings.
     */
    public static HystrixCommand.Setter buildForCircuitBreakerCommand( final String groupKey,
                                                                       final String commandKey,
                                                                       final boolean forceOpen ) {
        return HystrixCommand.Setter.withGroupKey( HystrixCommandGroupKey.Factory.asKey( groupKey ) )
                .andCommandKey( HystrixCommandKey.Factory.asKey( commandKey ) )
                .andCommandPropertiesDefaults( HystrixCommandProperties.Setter()
                .withCircuitBreakerForceOpen( forceOpen ) );
    }

    /**
     * Builds the necessary Hystrix configuration instance suitable for thread pool-based load shedding semantics.
     * @param groupKey the group key to associate the command to.
     * @return properly constructed Hystrix settings.
     */
    public static HystrixCommand.Setter buildForThreadPoolLoadShedderCommand( final String groupKey ) {
        return HystrixCommand.Setter.withGroupKey( HystrixCommandGroupKey.Factory.asKey( groupKey ) );
    }

    /**
     * Builds the necessary Hystrix configuration instance suitable for semaphore-based load shedding semantics.
     * @param groupKey the group key to associate the command to.
     * @return properly constructed Hystrix settings.
     */
    public static HystrixCommand.Setter buildForSemaphoreLoadShedderCommand( final String groupKey ) {
        return HystrixCommand.Setter.withGroupKey( HystrixCommandGroupKey.Factory.asKey( groupKey ) )
                .andCommandPropertiesDefaults( HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy( HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE ) );
    }

    /**
     * Builds the necessary Hystrix configuration instance suitable for fail fast semantics.
     * @param groupKey the group key to associate the command to.
     * @return properly constructed Hystrix settings.
     */
    public static HystrixCommand.Setter buildForFailFastCommand( final String groupKey ) {
        return HystrixCommand.Setter.withGroupKey( HystrixCommandGroupKey.Factory.asKey( groupKey ) );
    }

    /**
     * Builds the necessary Hystrix configuration instance suitable for fail silent semantics.
     * @param groupKey the group key to associate the command to.
     * @return properly constructed Hystrix settings.
     */
    public static HystrixCommand.Setter buildForFailSilentCommand( final String groupKey ) {
        return HystrixCommand.Setter.withGroupKey( HystrixCommandGroupKey.Factory.asKey( groupKey ) );
    }
}
