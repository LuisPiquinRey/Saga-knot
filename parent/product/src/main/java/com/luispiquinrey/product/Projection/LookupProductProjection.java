package com.luispiquinrey.product.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.ProductLookup;
import com.luispiquinrey.product.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Repository.RepositoryProductLookup;

@Component
@ProcessingGroup("product-collection")
public class LookupProductProjection {
    private final RepositoryProductLookup repositoryLookup;

    @Autowired
    public LookupProductProjection(RepositoryProductLookup repositoryLookup) {
        this.repositoryLookup = repositoryLookup;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        ProductLookup lookup=new ProductLookup();
        BeanUtils.copyProperties(productCreatedEvent,lookup);
        repositoryLookup.save(lookup);
    }
}
