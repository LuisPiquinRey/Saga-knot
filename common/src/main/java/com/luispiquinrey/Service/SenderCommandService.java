package com.luispiquinrey.Service;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SenderCommandService {
    
    @Autowired
    protected CommandGateway commandGateway;
    
    protected <T> CompletableFuture<T> send(Object command) {
        return commandGateway.send(command);
    }
    
    protected <T> T sendAndWait(Object command) {
        return commandGateway.sendAndWait(command);
    }
}