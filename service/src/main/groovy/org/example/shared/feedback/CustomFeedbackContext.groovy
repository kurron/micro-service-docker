package org.example.shared.feedback

/**
 * Message codes specific to this micro-service.  Make sure to select a series of message codes that are unique
 * from other micro-services.  Makes it easier to filter and create operation books to deal with specific
 * scenarios.
 **/
@SuppressWarnings( 'LineLength' )
enum CustomFeedbackContext implements FeedbackContext {

    PAYLOAD_TRANSFORMATION( 1000, 'Transforming byte payload into request', FeedbackLevel.DEBUG, Audience.DEVELOPMENT ),
    DOCUMENT_STORAGE( 1001, 'Storing document with message {}', FeedbackLevel.DEBUG, Audience.DEVELOPMENT ),
    SYNTHETIC_TRANSACTION_FAILURE( 1002, 'Problem executing synthetic transaction', FeedbackLevel.ERROR, Audience.OPERATIONS ),

    /**
     * Unique context code for this instance.
     */
    private final int theCode

    /**
     * Message format string for this instance.
     */
    private final String theFormatString

    /**
     * Feedback level for this instance.
     */
    private final FeedbackLevel theFeedbackLevel

    /**
     * The audience for this instance.
     */
    private final Audience theAudience

    CustomFeedbackContext( int aCode,
                           String aFormatString,
                           FeedbackLevel aFeedbackLevel,
                           Audience anAudience ) {
        theCode = aCode
        theFormatString = aFormatString
        theFeedbackLevel = aFeedbackLevel
        theAudience = anAudience
    }

    @Override
    int getCode() {
        theCode
    }

    @Override
    String getFormatString() {
        theFormatString
    }

    @Override
    FeedbackLevel getFeedbackLevel() {
        theFeedbackLevel
    }

    @Override
    Audience getAudience() {
        theAudience
    }
}
