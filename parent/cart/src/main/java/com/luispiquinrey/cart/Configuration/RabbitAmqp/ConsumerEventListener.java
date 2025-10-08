package com.luispiquinrey.cart.Configuration.RabbitAmqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.AsyncConsumerRestartedEvent;
import org.springframework.amqp.rabbit.listener.AsyncConsumerStartedEvent;
import org.springframework.amqp.rabbit.listener.ListenerContainerConsumerFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;


public class ConsumerEventListener implements ApplicationListener<ListenerContainerConsumerFailedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerEventListener.class);

    @Override
    public void onApplicationEvent(ListenerContainerConsumerFailedEvent event) {
        if (event.isFatal()) {
            logger.error("Fatal error occurred in consumer. Container: {}. Reason: {}",
                    event.getSource(), event.getReason());
        } else {
            logger.warn("Non-fatal error occurred in consumer. Retrying... Container: {}. Reason: {}",
                    event.getSource(), event.getReason());
        }
        Throwable throwable = event.getThrowable();
        if (throwable != null) {
            logger.error("Error details: ", throwable);
        }
    }

    @EventListener
    public void handleConsumerStarted(AsyncConsumerStartedEvent event) {
        logger.info("Consumer started. Container: {}", event.getSource());
    }

    @EventListener
    public void handleConsumerRestarted(AsyncConsumerRestartedEvent event) {
        logger.info("Consumer restarted. Container: {}", event.getSource());
    }
}
