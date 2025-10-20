package com.luispiquinrey.Service;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class SenderCommandService {

    public SenderCommandService() {
    }
    @Autowired
    private CommandGateway commandGateway;
    
    public <T> CompletableFuture<T> send(Object command) {
        return commandGateway.send(command);
    }
    public <T> T sendAndWait(Object command) {
        return commandGateway.sendAndWait(command);
    }
}