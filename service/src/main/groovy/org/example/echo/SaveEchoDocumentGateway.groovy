package org.example.echo

import static org.example.shared.resilience.Gateways.SAVE_ECHO_DOCUMENT

import com.netflix.hystrix.HystrixCommand
import org.example.shared.resilience.HystrixSettingsBuilder

/**
 * Resource gateway to a MongoDB resource.  Notice that there is no fallback logic.  That is because there isn't
 * anything useful you can return if the document could not be saved.  This can never bean an injected bean because
 * we need a new instance for each resource attempt.
 */
class SaveEchoDocumentGateway extends HystrixCommand<EchoDocument> {

    /**
     * Manages interactions with the database.
     */
    private final EchoDocumentRepository theRepository

    /**
     * The document we will attempt to save.
     */
    private final EchoDocument theDocument

    SaveEchoDocumentGateway( EchoDocumentRepository aRepository, EchoDocument aDocument) {
        super( HystrixSettingsBuilder.buildUsingDefaults( SAVE_ECHO_DOCUMENT.name() ) )
        theDocument = aDocument
        theRepository = aRepository
    }

    @Override
    protected EchoDocument run() throws Exception {
        theRepository.save( theDocument )
    }
}
