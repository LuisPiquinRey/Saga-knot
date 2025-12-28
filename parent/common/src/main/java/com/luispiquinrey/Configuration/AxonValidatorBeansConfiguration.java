package com.luispiquinrey.Configuration;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
@Configuration
public class AxonValidatorBeansConfiguration {

    @Bean
    public BeanValidationInterceptor<CommandMessage<?>> beanValidationInterceptor() {
        return new BeanValidationInterceptor<>();
    }

    @Autowired
    public void configure(
            CommandBus commandBus,
            @Lazy BeanValidationInterceptor<CommandMessage<?>> interceptor) {

        commandBus.registerDispatchInterceptor(interceptor);
    }
}
