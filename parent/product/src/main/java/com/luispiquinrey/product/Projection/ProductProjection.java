package com.luispiquinrey.product.Projection;

import com.luispiquinrey.common.Utilities.DataService;
import com.luispiquinrey.common.Utilities.IDataService;
import com.luispiquinrey.common.Utilities.RedisDecorator;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.DisallowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Event.ProductDeletedEvent;
import com.luispiquinrey.product.Event.ProductUpdatedEvent;


@Component
@ProcessingGroup("product-collection")
@DisallowReplay
public class ProductProjection {

    private DataService<Product,String> dataService;
    private IDataService<Product,String> iDataService;

    @Autowired
    public ProductProjection(DataService<Product,String> dataService,
                             RedisTemplate<String, Product> redisTemplate) {
        this.dataService = dataService;
        iDataService = new RedisDecorator<>(dataService, redisTemplate, Product.class);
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        Product product=new Product();
        BeanUtils.copyProperties(productCreatedEvent,product);
        iDataService.createTarget(product);
    }
    @EventHandler
    public void on(ProductUpdatedEvent event) {
        Product product = iDataService.findTargetById(event.getIdProduct())
                .orElseThrow(() -> new IllegalStateException(
                        "Product with ID " + event.getIdProduct() + " does not exist"));

        product.setName(event.getName());
        product.setBrand(event.getBrand());
        product.setCategories(event.getCategories());
        product.setGender(event.getGender());
        product.setPrice(event.getPrice());
        product.setStock(event.getStock());
        product.setStatus(event.getStatus());

        iDataService.updateTarget(product);
    }

    @EventHandler
    public void on(ProductDeletedEvent productDeletedEvent){
        iDataService.deleteTarget(productDeletedEvent.getIdProduct());
    }

}
