package org.example.shared.feedback

/**
 * Describes the intended set of "eye balls" that a feedback message is intended for.  Should allow us to filter
 * messages as needed.
 */
enum Audience {
    OPERATIONS,
    SUPPORT,
    QA,
    DEVELOPMENT
}
