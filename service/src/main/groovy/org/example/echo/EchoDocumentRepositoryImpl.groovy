package org.example.echo

import org.example.shared.BaseRepository
import org.springframework.data.mongodb.core.MongoOperations

/**
 * Implements any custom extensions to the repository.  The name must end in Impl in order for
 * Spring Data MongoDB to find it during startup.
 */
class EchoDocumentRepositoryImpl extends BaseRepository implements EchoDocumentRepositoryExtension {

    EchoDocumentRepositoryImpl( MongoOperations aTemplate ) {
        super( aTemplate )
    }
}
