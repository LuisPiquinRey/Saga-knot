package com.luispiquinrey.product.Configuration;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

public class RetryHandler implements ListenerInvocationErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(RetryHandler.class);
    private final int maxRetries;

    public RetryHandler(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public void onError(Exception exception,
                        EventMessage<?> event,
                        EventMessageHandler eventHandler) throws Exception {

        String eventType = event.getPayloadType().getSimpleName();
        String handlerName = eventHandler.getClass().getSimpleName();

        if (exception instanceof DataIntegrityViolationException) {
            log.warn("Skipping duplicate event [{}] (ID: {}) in handler [{}]: {}",
                    eventType,
                    event.getIdentifier(),
                    handlerName,
                    exception.getMessage());
            return;
        }


        log.error("Error processing event [{}] (ID: {}) in handler [{}]. Error: {}",
                eventType,
                event.getIdentifier(),
                handlerName,
                exception.getMessage(),
                exception);

        throw exception;
    }
}

