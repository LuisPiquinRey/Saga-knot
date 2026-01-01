package com.luispiquinrey.product.Handler.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.Projection.ProductLookup;
import com.luispiquinrey.product.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Event.ProductDeletedEvent;
import com.luispiquinrey.product.Event.ProductUpdatedEvent;
import com.luispiquinrey.product.Repository.RepositoryProductLookup;

import java.io.Serializable;

@Component
@ProcessingGroup("product-collection")
public class LookupProductProjection implements Serializable {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LookupProductProjection.class);
    private final RepositoryProductLookup repositoryLookup;

    @Autowired
    public LookupProductProjection(RepositoryProductLookup repositoryLookup) {
        this.repositoryLookup = repositoryLookup;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        log.info("Processing ProductCreatedEvent - ID: {}, Name: {}",
                productCreatedEvent.getIdProduct(), productCreatedEvent.getName());
        ProductLookup lookup=new ProductLookup();
        BeanUtils.copyProperties(productCreatedEvent,lookup);
        repositoryLookup.save(lookup);
        log.info("ProductLookup created successfully - ID: {}", productCreatedEvent.getIdProduct());
    }

    @EventHandler
    public void on(ProductUpdatedEvent event) {
        log.info("Processing ProductUpdatedEvent - ID: {}, Name: {}", event.getIdProduct(), event.getName());
        repositoryLookup.findById(event.getIdProduct()).ifPresentOrElse(
            lookup -> {
                BeanUtils.copyProperties(event, lookup);
                repositoryLookup.save(lookup);
                log.info("ProductLookup updated successfully - ID: {}", event.getIdProduct());
            },
            () -> log.warn("ProductLookup not found for update - ID: {}", event.getIdProduct())
        );
    }

    @EventHandler
    public void on(ProductDeletedEvent event) {
        log.info("Processing ProductDeletedEvent - ID: {}", event.getIdProduct());
        repositoryLookup.deleteById(event.getIdProduct());
        log.info("ProductLookup deleted successfully - ID: {}", event.getIdProduct());
    }
}
