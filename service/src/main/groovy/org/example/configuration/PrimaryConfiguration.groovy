package org.example.configuration

import org.example.ApplicationProperties
import org.example.echo.BytesToEchoRequestTransformer
import org.example.echo.DocumentWriter
import org.example.echo.EchoDocumentRepositoryImpl
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.integration.amqp.inbound.AmqpInboundGateway
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.handler.MessageHandlerChain
import org.springframework.integration.handler.ServiceActivatingHandler
import org.springframework.integration.support.json.Jackson2JsonObjectMapper
import org.springframework.integration.transformer.MessageTransformingHandler
import org.springframework.messaging.MessageHandler
import org.springframework.web.client.RestTemplate

/**
 * Spring context configuration that contains the definitions of application's beans.  Be aware
 * that Spring Boot will automatically create bean in addition to those that we define.
 */
@Configuration
@EnableConfigurationProperties( ApplicationProperties )
class PrimaryConfiguration {

    /**
     * Application-specific properties.
     */
    @Autowired
    ApplicationProperties properties

    /**
     * The MongoDB template that Spring Boot creates for us.
     */
    @Autowired
    MongoOperations mongoOperations

    /**
     * The RabbitMQ connection manager.
     */
    @Autowired
    ConnectionFactory connectionFactory

    @Bean
    FeedbackAwareBeanPostProcessor feedbackAwareBeanPostProcessor() {
        new FeedbackAwareBeanPostProcessor( properties.applicationType )
    }

    @Bean
    EchoDocumentRepositoryImpl echoDocumentRepositoryImpl() {
        new EchoDocumentRepositoryImpl( mongoOperations )
    }

    @Bean
    Queue echoQueue() {
        new Queue( properties.queue )
    }

    @Bean
    DirectChannel uploadChannel() {
        def bean = new DirectChannel()
        bean.subscribe( uploadMessageHandlerChain() )
        bean
    }

    @Bean
    SimpleMessageListenerContainer uploadListenerContainer() {
        def bean = new SimpleMessageListenerContainer( connectionFactory )
        bean.queueNames = [properties.queue]
        bean
    }

    @Bean
    AmqpInboundGateway uploadInboundGateway() {
        def bean = new AmqpInboundGateway( uploadListenerContainer() )
        bean.requestChannel = uploadChannel()
        // a robust solution would also set up an error channel to handle failures
        bean
    }

    @Bean
    MessageHandlerChain uploadMessageHandlerChain() {
        def bean = new MessageHandlerChain()
        List<MessageHandler> handlers = []
        handlers << new MessageTransformingHandler( bytesToEchoRequestTransformer() )
        handlers << new ServiceActivatingHandler( documentWriter() )
        bean.handlers = handlers
        bean
    }

    @Bean
    BytesToEchoRequestTransformer bytesToEchoRequestTransformer() {
        new BytesToEchoRequestTransformer()
    }

    @Bean
    DocumentWriter documentWriter() {
        new DocumentWriter()
    }

    @Bean
    Jackson2JsonObjectMapper jackson2JsonObjectMapper() {
        new Jackson2JsonObjectMapper()
    }

    @Bean
    RestTemplate restTemplate() {
        new RestTemplate()
    }
}
