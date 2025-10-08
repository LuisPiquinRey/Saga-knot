package com.luispiquinrey.product.Configuration;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.springframework.stereotype.Component;

@Component
public class EventErrorHandler implements ListenerInvocationErrorHandler{

    @Override
    public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {
        throw exception;
    }
}
