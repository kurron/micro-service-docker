package org.example.shared.feedback

/**
 * Convenience base class for objects that wish to have a feedback provider injected into them.
 */
class BaseFeedbackAware implements FeedbackAware {

    /**
     * The provider to use.
     */
    private FeedbackProvider theFeedbackProvider

    protected BaseFeedbackAware( ) {
        theFeedbackProvider = new NullFeedbackProvider()
    }

    @Override
    FeedbackProvider getFeedbackProvider( ) {
        theFeedbackProvider
    }

    @Override
    void setFeedbackProvider( FeedbackProvider aProvider ) {
        theFeedbackProvider = aProvider
    }
}
