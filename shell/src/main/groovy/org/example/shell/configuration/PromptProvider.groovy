package org.example.shell.configuration

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.shell.plugin.support.DefaultPromptProvider
import org.springframework.stereotype.Component

/**
 * Customizes the prompt the shell uses.
 */
@SuppressWarnings( ['GroovyUnusedDeclaration', 'GetterMethodCouldBeProperty'] )
@Component
@Order( Ordered.HIGHEST_PRECEDENCE )
class PromptProvider extends DefaultPromptProvider {

    @Override
    String getPrompt() { 'example-micro-service-shell> ' }

    @Override
    String getProviderName() { 'Example Micro-Service Shell prompt' }
}
