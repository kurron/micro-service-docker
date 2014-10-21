package org.example

import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * Driver for user acceptance tests.
 */
@Cucumber.Options( tags = ['@api'],
                   strict = true,
                   format = ['pretty', 'html:build/reports/cucumber'],
//                   glue = ['src/test/groovy'],
                   monochrome = true,
                   features = ['src/test/resources'] )
@RunWith( Cucumber )
class ApiAcceptanceTest {
}
