package com.luispiquinrey.product.Configuration;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventErrorHandler implements ListenerInvocationErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(EventErrorHandler.class);

    @Override
    public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {
        logger.error("Error handling event [{}] in handler [{}]",
                event.getPayloadType().getSimpleName(),
                eventHandler.getClass().getSimpleName(), exception);

        throw exception;
    }
}