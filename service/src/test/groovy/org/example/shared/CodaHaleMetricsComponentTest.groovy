package org.example.shared

import com.codahale.metrics.MetricRegistry
import org.springframework.beans.factory.annotation.Autowired

/**
 * A learning test to see how Spring integrates with the Coda Hale library.
 */
class CodaHaleMetricsComponentTest extends BaseComponentTest {

    @Autowired
    private CodaHaleMetricsExample sut

    @Autowired
    private MetricRegistry registry

    def 'exercise data generation'() {
        given: 'valid subjects under test'
        assert sut
        assert registry

        when: 'data is generated'
        1000.times {
            sut.generateData()
        }

        then: 'metrics exist in the registry'
        registry.counters.keySet()
        registry.gauges.keySet()
    }
}
