package org.example.configuration

import org.example.ApplicationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * Spring configuration for the web portion of the application.
 */
@Configuration
@EnableConfigurationProperties( ApplicationProperties )
class WebConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    EmbeddedServletContainerEventListener embeddedServletContainerEventListener() {
        new EmbeddedServletContainerEventListener()
    }

    @Bean
    ContextClosedEventListener contextClosedEventListener() {
        new ContextClosedEventListener()
    }

    @Bean
    HealthController healthController() {
        new HealthController()
    }
}
