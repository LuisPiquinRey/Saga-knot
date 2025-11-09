package com.luispiquinrey.product.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Event.ProductDeletedEvent;
import com.luispiquinrey.product.Event.ProductUpdatedEvent;
import com.luispiquinrey.product.Service.ProductService;

@Component
@ProcessingGroup("product-collection")
public class ProductProjection {

    private final ProductService productService;

    @Autowired
    public ProductProjection(ProductService productService) {
        this.productService = productService;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        Product product=new Product();
        BeanUtils.copyProperties(productCreatedEvent,product);
        productService.createTarget(product);
    }
    @EventHandler
    public void on(ProductUpdatedEvent event) {
        productService.findTargetById(event.getIdProduct()).ifPresent(product -> {
            product.setName(event.getName());
            product.setBrand(event.getBrand());
            product.setCategories(event.getCategories());
            product.setGender(event.getGender());
            product.setPrice(event.getPrice());
            product.setStock(event.getStock());
            product.setStatus(event.getStatus());
            productService.createTarget(product);
        });
    }

    @EventHandler
    public void on(ProductDeletedEvent productDeletedEvent){
        productService.deleteTarget(productDeletedEvent.getIdProduct());
    }

}
