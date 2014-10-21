package org.example.configuration

import groovy.util.logging.Slf4j
import org.example.ApplicationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent
import org.springframework.context.ApplicationListener

/**
 * Listens to container events so we can pull out the HTTP port.
 */
@Slf4j
class EmbeddedServletContainerEventListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    /**
     * Application-specific properties.
     */
    @Autowired
    ApplicationProperties properties

    @Override
    void onApplicationEvent( EmbeddedServletContainerInitializedEvent event ) {
        properties.httpListeningPort = event.embeddedServletContainer.port
        log.info( 'Micro-Service is listening on port {}', properties.httpListeningPort )
    }
}
