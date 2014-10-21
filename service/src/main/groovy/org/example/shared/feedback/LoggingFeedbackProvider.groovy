package org.example.shared.feedback

import org.slf4j.Logger
import org.slf4j.MDC
import org.slf4j.MarkerFactory

/**
 * Knows how to take a feedback context and send it off to the logging system.
 */
class LoggingFeedbackProvider implements FeedbackProvider {
    /**
     * MDC key holding the message code.
     */
    private static final String MESSAGE_CODE = 'message-code'

    /**
     * MDC key holding the application type.
     */
    private static final String APP_TYPE = 'application-type'

    /**
     * Sends the message off to the logging server.
     */
    private final Logger theLogger

    /**
     * Unique identifier for this application, eg. CAFE or SUSH-E.
     */
    private final String theApplicationType

    LoggingFeedbackProvider( Logger aLogger, String anApplicationType ) {
        if ( null == aLogger ) {
            throw new IllegalArgumentException( 'Logger may not be null!' )
        }
        if ( null == anApplicationType ) {
            throw new IllegalArgumentException( 'Application ID may not be null!' )
        }
        theLogger = aLogger
        theApplicationType = anApplicationType
    }

    void sendFeedback( FeedbackContext context, Object[] arguments ) {
        verifyParameters( context )
        storeMessageCode( context )
        storeApplicationType()

        try {
            switch ( context.feedbackLevel )
            {
                case FeedbackLevel.TRACE:
                    if ( theLogger.isTraceEnabled() ) {
                        theLogger.trace( MarkerFactory.getMarker( context.audience.name() ),
                                context.formatString,
                                arguments )
                    }
                    break
                case FeedbackLevel.DEBUG:
                    if ( theLogger.isDebugEnabled() ) {
                        theLogger.debug( MarkerFactory.getMarker( context.audience.name() ),
                                context.formatString,
                                arguments )
                    }
                    break
                case FeedbackLevel.INFO:
                    if ( theLogger.isInfoEnabled() ) {
                        theLogger.info( MarkerFactory.getMarker( context.audience.name() ),
                                context.formatString,
                                arguments )
                    }
                    break
                case FeedbackLevel.WARN:
                    if ( theLogger.isWarnEnabled() ) {
                        theLogger.warn( MarkerFactory.getMarker( context.audience.name() ),
                                context.formatString,
                                arguments )
                    }
                    break
                case FeedbackLevel.ERROR:
                    if ( theLogger.isErrorEnabled() ) {
                        theLogger.error( MarkerFactory.getMarker( context.audience.name() ),
                                context.formatString,
                                arguments )
                    }
            }
        }
        finally {
            clearMessageCode()
            // no need to clear the application type since it is fixed for duration of the process
        }
    }

    void sendFeedback(  FeedbackContext context, Throwable error ) {
        verifyParameters( context )
        storeMessageCode( context )

        try {
            switch ( context.feedbackLevel )
            {
                case FeedbackLevel.TRACE: throw new IllegalArgumentException( 'Level may not be TRACE.' )
                case FeedbackLevel.DEBUG: throw new IllegalArgumentException( 'Level may not be DEBUG.' )
                case FeedbackLevel.INFO: throw new IllegalArgumentException( 'Level may not be INFO.' )
                case FeedbackLevel.WARN:
                    if ( theLogger.isWarnEnabled() ) {
                        theLogger.warn( MarkerFactory.getMarker( context.audience.name() ),
                                context.formatString,
                                error )
                    }
                    break
                case FeedbackLevel.ERROR:
                    if ( theLogger.isErrorEnabled() ) {
                        theLogger.error( MarkerFactory.getMarker( context.audience.name() ),
                                context.formatString,
                                error )
                    }
            }
        }
        finally {
            clearMessageCode()
            // no need to clear the application type since it is fixed for duration of the process
        }
    }

    private static void clearMessageCode() {
        MDC.remove( MESSAGE_CODE )
    }

    private static void storeMessageCode(FeedbackContext context) {
        MDC.put( MESSAGE_CODE, Integer.toString( context.code ) )
    }

    private void storeApplicationType() {
        MDC.put( APP_TYPE, theApplicationType )
    }

    private static void verifyParameters( FeedbackContext context ) {
        if ( null == context ) {
            throw new IllegalArgumentException( 'Context may not be null!' )
        }
    }
}
