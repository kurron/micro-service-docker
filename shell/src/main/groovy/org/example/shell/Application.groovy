package org.example.shell

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.shell.Bootstrap

/**
 * Application driver.
 */
@SuppressWarnings( 'GrMethodMayBeStatic' )
@ComponentScan( ['org.example'] )
@Configuration
@PropertySource( 'classpath:/config/application.properties' )
class Application {

    /**
     * Starts the entire application.
     * @param args
     */
    static void main( String[] args ) {
        Bootstrap.main( args )
    }

    @Autowired
    private ApplicationProperties properties

    // Necessary because Spring does not create one automatically for you.
    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        new PropertySourcesPlaceholderConfigurer()
    }

    @Bean
    ObjectMapper objectMapper() {
        new ObjectMapper()
    }

    @Bean
    ConnectionFactory connectionFactory() {
        def bean = new CachingConnectionFactory( properties.rabbitHost, properties.rabbitPort )
        bean.publisherConfirms = false
        bean.publisherReturns = false
        bean.username = properties.rabbitUsername
        bean.password = properties.rabbitPassword
        bean.virtualHost = properties.rabbitVirtualHost
        bean
    }

    @Bean
    AmqpAdmin amqpAdmin() {
        new RabbitAdmin( connectionFactory() )
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        new RabbitTemplate( connectionFactory() )
    }
}
