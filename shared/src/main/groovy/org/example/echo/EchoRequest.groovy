package org.example.echo

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Message to send to the echo service.
 */
@Canonical
class EchoRequest {

    /**
     * Mime-Type of the request.
     */
    @JsonIgnore
    static final String CONTENT_TYPE = 'application/json;type=echo-request;version=1.0.0'

    /**
     * Text to store.
     */
    @JsonProperty( 'message' )
    String message
}
