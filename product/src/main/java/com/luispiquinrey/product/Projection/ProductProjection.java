package com.luispiquinrey.product.Projection;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Command.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Repository.RepositoryProduct;

@Component
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
}
