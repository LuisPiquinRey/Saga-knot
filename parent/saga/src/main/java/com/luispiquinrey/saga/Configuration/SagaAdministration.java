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

import com.luispiquinrey.Enums.StatusProduct;
import com.luispiquinrey.Event.AddedProductToOrderEvent;

import org.axonframework.commandhandling.gateway.CommandGateway;
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

        commandGateway.send(addProductToOrderCommand);
    }

    @SagaEventHandler(associationProperty = "idProduct")
    public void on(AddedProductToOrderEvent event) {
        UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                .idProduct(event.getIdItem()) 
                .status(StatusProduct.ADDED_TO_ORDER)
                .build();

        commandGateway.send(updateProductCommand);
    }
}
