package com.luispiquinrey.saga.Configuration;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.luispiquinrey.Command.AddProductToOrderCommand;
import com.luispiquinrey.Command.DeleteProductCommand;
import com.luispiquinrey.Command.UpdateProductCommand;
import com.luispiquinrey.Event.ProductRequestedAddToOrderEvent;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class SagaAdministration {

    private transient CommandGateway commandGateway;

    @Autowired
    public SagaAdministration(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "idProduct")
    public void on(ProductRequestedAddToOrderEvent event) {
        AddProductToOrderCommand addProductToOrderCommand = AddProductToOrderCommand.builder()
                .idItem(event.getIdItem())
                .idOrder(event.getIdOrder())
                .quantity(event.getStock())
                .total(event.getPrice())
                .build();

        commandGateway.send(addProductToOrderCommand, new CommandCallback<AddProductToOrderCommand, Object>() {
            @Override
            public void onResult(CommandMessage<? extends AddProductToOrderCommand> commandMessage,
                    CommandResultMessage<? extends Object> commandResultMessage) {
                if (commandResultMessage.isExceptional()) {
                    if (event.getStock() > 0) {
                        UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                                .idProduct(event.getIdItem())
                                .brand(event.getBrand())
                                .name(event.getName())
                                .price(event.getPrice())
                                .stock(event.getStock() - 1)
                                .build();
                        commandGateway.send(updateProductCommand);
                    } else {
                        DeleteProductCommand deleteProductCommand = DeleteProductCommand.builder()
                                .idProduct(event.getIdItem())
                                .build();
                        commandGateway.send(deleteProductCommand);
                    }
                }
            }
        });
    }
}
