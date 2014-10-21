package org.example.shared.feedback

/**
 * Interface Injection: any bean implementing this interface is indicating that it was a feedback provider injected into
 * it.
 */
interface FeedbackAware {
    /**
     * The provider the instance should use.
     * @return the provider instance.
     */
    FeedbackProvider getFeedbackProvider()

    /**
     * Specifies the provider this instance should use.
     * @param aProvider the provider to use.
     */
    void setFeedbackProvider( FeedbackProvider aProvider )
}
