package com.luispiquinrey.user.Configuration;

import java.util.logging.Logger;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class ConfigurationRabbit {

    private static final Logger LOGGER = Logger.getLogger(ConfigurationRabbit.class.getName());

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory());
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        template.setRetryTemplate(retryTemplate);
        template.setExchange("exchange-order-user");
        template.setDefaultReceiveQueue("queue-order-server");
        template.setRoutingKey("routing-key-order-user");
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                LOGGER.info("Message confirmed: " + correlationData.getId());
            } else {
                LOGGER.info("Message not confirmed: " + cause);
            }
        });

        return template;
    }
}
