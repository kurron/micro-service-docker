package org.example.shared.feedback

import org.slf4j.helpers.MessageFormatter

/**
 * An unrecoverable error is a failure in the operating environment where further processing is likely to be futile,
 * such as running out of memory.  A developer isn't supposed to be able to recover from these type of conditions and
 * is likely to ask the user what the proper course of action is.  For example, if we run out of disk space then the
 * program will have to tell the user and give them an opportunity to free up some space.  Another example might be a
 * programming error where we cast an object to the wrong type.  No recovering from that.
 */
class UnrecoverableError extends RuntimeException {

    /**
     * Unique error code.
     */
    final int code

    protected UnrecoverableError( FeedbackContext context, Object[] arguments ) {
        super( MessageFormatter.arrayFormat( context.formatString, arguments ).message )
        code = context.code
    }
}
