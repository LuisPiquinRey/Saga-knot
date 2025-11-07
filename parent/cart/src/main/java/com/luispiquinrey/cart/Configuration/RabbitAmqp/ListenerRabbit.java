package com.luispiquinrey.cart.Configuration.RabbitAmqp;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.luispiquinrey.cart.Command.CreateCartCommand;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "queue-order-server", durable = "true"),
        exchange = @Exchange(value = "exchange-order-user", ignoreDeclarationExceptions = "true"),
        key = "routing-key-order-user")
)
public class ListenerRabbit {

    private final CommandGateway commandGateway;

    public ListenerRabbit(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
    @RabbitHandler
    public void processOrder(Long idContact) {
        log.info("Listener received idContact -> {}", idContact);
        
        CreateCartCommand createCartCommand = CreateCartCommand.builder()
                .idUser(idContact)
                .build();
        commandGateway.send(createCartCommand);
    }
}