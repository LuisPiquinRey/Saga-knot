package com.luispiquinrey.cart.Configuration.RabbitAmqp;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerRabbit {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-order-server", durable = "true"),
            exchange = @Exchange(value = "exchange-order-user", ignoreDeclarationExceptions = "true"),
            key = "routing-key-order-user")
    )
    public void processOrder() {
    }
}
