package com.luispiquinrey.saga.Configuration;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.luispiquinrey.Command.ReserveItemCommand;
import com.luispiquinrey.Event.ProductCreatedEvent;

@Saga
public class SagaAdministration {
    private transient CommandGateway commandGateway;

    @Autowired
    public SagaAdministration(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
    @StartSaga
    @SagaEventHandler(associationProperty="idProduct")
    public void handle(ProductCreatedEvent productCreatedEvent){
        ReserveItemCommand reserveItemCommand=ReserveItemCommand.builder().build();
        BeanUtils.copyProperties(productCreatedEvent, reserveItemCommand);
        commandGateway.send(reserveItemCommand);
    }

}
