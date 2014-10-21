package org.example.shared.feedback

import org.slf4j.helpers.MessageFormatter

/**
 * Signals a departure from typical processing that can happen during normal program
 * operation.  Said another way, deviations are expected "error" conditions that developers should be able to handle in
 * their logic. Having a user type a first name into a SSN field might be considered a Deviation.  The inability to
 * contact a server might be treated as a deviation if the network connectivity is known to be suspect and the
 * application is required to deal with that fact.
 */
class Deviation extends RuntimeException {

    /**
     * Unique error code.
     */
    final int code

    protected Deviation( FeedbackContext context, Object[] arguments ) {
        super( MessageFormatter.arrayFormat( context.formatString, arguments ).message )
        code = context.code
    }
}
