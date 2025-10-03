package com.luispiquinrey.product.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Repository.RepositoryProduct;

@Component
@ProcessingGroup("product-collection")
public class ProductProjection {
    
    private final RepositoryProduct repositoryProduct;

    @Autowired
    public ProductProjection(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        Product product=new Product();
        BeanUtils.copyProperties(productCreatedEvent,product);
        repositoryProduct.save(product);
    }
    @ExceptionHandler(resultType=Exception.class)
    public void handle(Exception exception){
        System.err.println("General exception in ProductProjection: " + exception.getMessage());
    }
    @ExceptionHandler(resultType=IllegalArgumentException.class)
    public void on(IllegalArgumentException exception){
        System.err.println("IllegalArgumentException in ProductProjection: " + exception.getMessage());
    }
}
