package org.example.echo

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Spring Data MongoDB repository to manipulate the Echo Document collection.
 */
interface EchoDocumentRepository extends MongoRepository<EchoDocument, String>, EchoDocumentRepositoryExtension {

}
