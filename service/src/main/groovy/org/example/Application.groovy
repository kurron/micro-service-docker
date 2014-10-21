package org.example

import groovy.util.logging.Slf4j
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan

/**
 * Application launcher that can operate as either a self-executing WAR or embedded inside a servlet container.
 */
@EnableAutoConfiguration
@ComponentScan
@Slf4j
class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder builder ) {
        log.info( '--- Running inside provided web container. ----' )
        builder.sources( Application ).web( true ).headless( true )
    }

    /**
     * Called to start the entire application.  Typically, java -jar foo.jar.
     * @param args any arguments to the program.
     */
    static void main( String[] args ) {
        log.info( '--- Running embedded web container ----' )
        SpringApplication.run( Application, args )
    }
}
