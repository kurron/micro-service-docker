package org.example.echo

import groovy.transform.Canonical
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

/**
 * Simple MongoDB document that records any echo messages that the system hears.
 */
@Document
@CompoundIndexes( [ @CompoundIndex( name = 'optimistic_concurrency_idx', def = "{ '_id': 1, 'version': 1 }" ) ] )
@Canonical( excludes = ['id', 'version'] )
class EchoDocument {
    /**
     * The required MongoDB primary key.
     */
    @Id
    @Field( '_id' )
    String id

    /**
     * A version field used to implement optimistic locking on entities.
     */
    @Version
    @Field( 'version' )
    Long version

    /**
     * When the message was heard, UTC datetime.
     */
    @Field( 'datetime' )
    long datetime

    /**
     * Contents of the message.
     */
    @Field( 'message' )
    String message
}
