package com.luispiquinrey.product;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.luispiquinrey.product.Configuration.CommandInterceptorProduct;
import com.luispiquinrey.product.Configuration.EventErrorHandler;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.luispiquinrey.Error.Handler", "com.luispiquinrey.product.*"})
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}
	@Autowired
	public void registerInterceptorCommandProduct(ApplicationContext context, CommandBus commandBus){
		commandBus.registerDispatchInterceptor(context.getBean(CommandInterceptorProduct.class));
	}
	@Autowired
	public void configure(EventProcessingConfigurer config){
		config.registerListenerInvocationErrorHandler("product-collection", conf-> new EventErrorHandler());
	}
}
