package com.luispiquinrey.cart.Configuration.RabbitAmqp;

import java.util.logging.Logger;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationRabbit {

    private static final Logger LOGGER = Logger.getLogger(ConfigurationRabbit.class.getName());

    @Bean
    public Queue queue() {
        return new Queue("queue-order-server", true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("exchange-order-user");
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange) {
        return new Binding("queue-order-server", Binding.DestinationType.QUEUE,
                "exchange-order-user", "routing-key-order-user", null);
    }

        @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
}
