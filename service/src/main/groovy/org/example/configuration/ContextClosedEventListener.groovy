package org.example.configuration

import ch.qos.logback.classic.LoggerContext
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent

/**
 * Listens to container events so we can shutdown the logging context, ensuring messages get flushed prior to the process going away.
 */
class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    void onApplicationEvent( final ContextClosedEvent event ) {
        LoggerContext loggerContext = LoggerFactory.ILoggerFactory as LoggerContext
        loggerContext.stop()
    }
}
