package org.example.echo

import cucumber.api.PendingException
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.example.shared.BaseStepDefinition
import org.springframework.amqp.core.MessageBuilder
import org.springframework.beans.factory.annotation.Autowired

/**
 * Implementation of the Echo Service feature steps.
 */
class EchoServiceStepDefinitions extends BaseStepDefinition {

    /**
     * Manages interactions with MongoDB.
     */
    @Autowired
    EchoDocumentRepository repository

    /**
     * Message to send.
     */
    EchoRequest request

    /**
     * Message to receive.
     */
    EchoResponse response

    @Given( '^a text message I want to store$' )
    void a_text_message_I_want_to_store() throws Throwable {
        request = new EchoRequest( UUID.randomUUID().toString() )
    }

    @When( '^I call the echo message service$' )
    void i_call_the_echo_message_service() throws Throwable {
        def message = MessageBuilder.withBody( objectMapper.toJson( request ).bytes )
                                    .setContentType( 'application/json;type=echo-request' )
                                    .setMessageId( UUID.randomUUID().toString() )
                                    .setTimestamp( Calendar.getInstance( TimeZone.getTimeZone( 'UTC' ) ).time )
                                    .setAppId( 'send-test' )
                                    .setCorrelationId( UUID.randomUUID().toString().bytes )
                                    .build()
        // use the default exchange and the routing key is the queue name
        def responseMessage = rabbitOperations.sendAndReceive( properties.queue, message )
        response = objectMapper.fromJson( responseMessage.body, EchoResponse )
    }

    @Then( '^my message should be stored in the system$' )
    void my_message_should_be_stored_in_the_system() throws Throwable {
        assert response
        def found = repository.findOne( response.id )
        assert found
        assert found.message == request.message
    }

    @Given('^a previously echoed message$')
    void a_previously_echoed_message() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @When('^I call the fetch message service$')
    void i_call_the_fetch_message_service() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @Then('^my message should be downloaded$')
    void my_message_should_be_downloaded() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

}
