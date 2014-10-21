package org.example.configuration

import org.example.shared.feedback.FeedbackAware
import org.example.shared.feedback.LoggingFeedbackProvider
import org.slf4j.LoggerFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor

/**
 * Spring bean post processor that injects a feedback provider into beans that want it.
 */
class FeedbackAwareBeanPostProcessor implements BeanPostProcessor {
    /**
     * Unique indicator of the application emitting the messages.
     */
    private final String theApplicationType

    FeedbackAwareBeanPostProcessor( String applicationType ) {
        theApplicationType = applicationType
    }

    @Override
    Object postProcessBeforeInitialization( Object bean, String beanName) throws BeansException {
        if ( bean instanceof FeedbackAware ) {
            def provider = new LoggingFeedbackProvider( LoggerFactory.getLogger( bean.class ), theApplicationType )
            bean.feedbackProvider = provider
        }
        bean
    }

    @Override
    Object postProcessAfterInitialization( Object bean, String beanName) throws BeansException {
        bean
    }
}
