package org.example.shared.feedback

/**
 * This interface describes a contract for objects that are intended to send feedback through the system, including
 * logged messages and exceptions.
 */
interface FeedbackContext {
    /**
     * Unique code for the context, usually a message or error code.
     * @return this context's code.
     */
    int getCode()

    /**
     * The format string that can be understood by the SLF4J logging system. Is used to generate
     * both log messages and messages contained in exceptions.
     * @return this context's SLF4J format string.
     */
    String getFormatString()

    /**
     * What feedback level to use when constructing feedback from this context.
     * @return this context's feedback level.
     */
    FeedbackLevel getFeedbackLevel()

    /**
     * Describes the intended viewing audience for this feedback.
     * @return this context's intended audience.
     */
    Audience getAudience()
}
