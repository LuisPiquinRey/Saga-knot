package com.luispiquinrey.cart.Configuration.RabbitAmqp;

import java.util.Collections;
import java.util.logging.Logger;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
    public CachingConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitConnectionFactory());
        container.setQueues(queue());
        container.setMessageListener(knotListener());
        container.setConsumerArguments(Collections.singletonMap("x-priority", Integer.valueOf(10)));
        return container;
    }

    @Bean
    public MessageListener knotListener() {
        return new MessageListener() {
            @Override
            public void onMessage(Message message) {
                LOGGER.info("received: " + message);
            }
        };
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}
/*
 * @Configuration
@EnableRabbit
public class Config implements RabbitListenerConfigurer {
    @Autowired
    private LocalValidatorFactoryBean validator;
    ...
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
      registrar.setValidator(this.validator);
    }
}
    ACUERDATE DE AGREGAR ESTO
 */