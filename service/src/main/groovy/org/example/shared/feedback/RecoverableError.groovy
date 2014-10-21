package org.example.shared.feedback

import org.slf4j.helpers.MessageFormatter

/**
 * A recoverable error is a failure in the operating environment where the program can continue to operate, but likely
 * at a reduced capacity.  An example might be the inability to contact a database server that is always supposed to be
 * available.  The programmer is expected to attempt some sort recovery, such as contacting a secondary server, instead
 * of just letting the error bubble up.  If recovery fails, then the recoverable error should be converted to a
 * a unrecoverable error and bubbled up.
 */
class RecoverableError extends RuntimeException {

    /**
     * Unique error code.
     */
    final int code

    protected RecoverableError( FeedbackContext context, Object[] arguments ) {
        super( MessageFormatter.arrayFormat( context.formatString, arguments ).message )
        code = context.code
    }
}
