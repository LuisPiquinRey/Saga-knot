package com.luispiquinrey.product.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Entities.Lookup;
import com.luispiquinrey.product.Repository.RepositoryLookup;

@Component
@ProcessingGroup("product-collection")
public class LookupProjection {
    private final RepositoryLookup repositoryLookup;

    @Autowired
    public LookupProjection(RepositoryLookup repositoryLookup) {
        this.repositoryLookup = repositoryLookup;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        Lookup lookup=new Lookup();
        BeanUtils.copyProperties(productCreatedEvent,lookup);
        repositoryLookup.save(lookup);
    }
}
