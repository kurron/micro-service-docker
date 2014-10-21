package org.example.echo

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Message returned from the echo service.
 */
@Canonical
class EchoResponse {

    /**
     * The Mime-Type of the document.
     */
    @JsonIgnore
    static final String CONTENT_TYPE = 'application/json;type=echo-response;version=0.0.0'

    /**
     * Unique identifier of the stored message.
     */
    @JsonProperty( 'id' )
    String id
}
