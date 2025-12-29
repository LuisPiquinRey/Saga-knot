package com.luispiquinrey.product.Configuration;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryHandler implements ListenerInvocationErrorHandler {

    private static final Logger log =
            LoggerFactory.getLogger(RetryHandler.class);
    final int maxRetries;
    int retryCount = 0;
    public RetryHandler(int maxRetries) {
        this.maxRetries = maxRetries;
    }
    @Override
    public void onError(Exception exception,
                        EventMessage<?> event,
                        EventMessageHandler eventHandler) throws Exception {

        if (exception instanceof IllegalStateException) {
            log.warn(
                    "Skipping event [{}] in handler [{}]. Reason: {}",
                    event.getPayloadType().getSimpleName(),
                    eventHandler.getClass().getSimpleName(),
                    exception.getMessage()
            );
        }
        if(retryCount < maxRetries) {
            retryCount++;
            log.info("Retrying event [{}] in handler [{}]. Attempt {}/{}",
                    event.getPayloadType().getSimpleName(),
                    eventHandler.getClass().getSimpleName(),
                    retryCount,
                    maxRetries);
            eventHandler.handle(event);
        }else{
            log.error("Max retries reached for event [{}] in handler [{}]. Cancelling further processing.",
                    event.getPayloadType().getSimpleName(),
                    eventHandler.getClass().getSimpleName());
        }
        throw exception;
    }
}

