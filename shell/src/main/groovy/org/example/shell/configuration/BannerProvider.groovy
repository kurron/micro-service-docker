package org.example.shell.configuration

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.shell.plugin.support.DefaultBannerProvider
import org.springframework.shell.support.util.OsUtils
import org.springframework.stereotype.Component

/**
 * Provides a customized banner that the shell will display at startup.
 */
@SuppressWarnings( ['GroovyUnusedDeclaration', 'UnnecessaryGetter', 'GetterMethodCouldBeProperty'] )
@Component
@Order( Ordered.HIGHEST_PRECEDENCE )
class BannerProvider extends DefaultBannerProvider  {

    String getBanner() {
        '''
BANNER TEXT GOES HERE
''' + OsUtils.LINE_SEPARATOR + getVersion() + OsUtils.LINE_SEPARATOR
    }

    String getVersion() { ':: Example Micro-Service Command Shell :: (v1.0.0)' }

    String getWelcomeMessage() { 'Welcome to Example Micro-Service Shell. For assistance press or type "hint" then hit ENTER.' }

}
