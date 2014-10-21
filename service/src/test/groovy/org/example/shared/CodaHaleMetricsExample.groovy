package org.example.shared

import java.security.SecureRandom
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.CounterService
import org.springframework.boot.actuate.metrics.GaugeService
import org.springframework.stereotype.Component

/**
 * Example bean that show cases Coda Hale metrics integration.
 */
@Component
class CodaHaleMetricsExample {

    /**
     * Random data generator.
     */
    private static final SecureRandom RANDOM = new SecureRandom()

    /**
     * Used to track counts.
     */
    @Autowired
    private CounterService counterService

    /**
     * Used to track values.
     */
    @Autowired
    private GaugeService gaugeService

    /**
     * An example of the different types of metrics we can keep track of.
     *
     * <ul>
     *     <li>A gauge is an instantaneous measurement of a value. For example, we may want to measure the number of
     *     pending jobs in a queue.</li>
     *     <li>A counter is just a gauge for an AtomicLong instance. You can increment or decrement its value. </li>
     *     <li>A meter measures the rate of events over time (e.g., “requests per second”). In addition to the mean
     *         rate, meters also track 1-, 5-, and 15-minute moving averages.</li>
     *     <li>A histogram measures the statistical distribution of values in a stream of data. In addition to minimum,
     *         maximum, mean, etc., it also measures median, 75th, 90th, 95th, 98th, 99th, and 99.9th percentiles.</li>
     *     <li>A timer measures both the rate that a particular piece of code is called and the distribution of its
     *         duration.</li>
     * </ul>
     *
     * The names of the metrics dictate how Spring stores the metrics.
     *
     * @see org.springframework.boot.actuate.metrics.writer.CodahaleMetricWriter
     */
    void generateData() {
        // sent to Coda Hale meter
        counterService.increment( 'meter.example.counter' )

        // sent to Spring counter
        counterService.increment( 'example.counter' )

        // sent to Coda Hale histogram
        gaugeService.submit( 'histogram.example.gauge', RANDOM.nextDouble() )

        // sent to Coda Hale timer
        gaugeService.submit( 'timer.example.gauge', RANDOM.nextDouble() )

        // sent to Spring gauge
        gaugeService.submit( 'example.gauge', RANDOM.nextDouble() )
    }
}
