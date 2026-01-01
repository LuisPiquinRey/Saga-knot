package com.luispiquinrey.product.Handler.Projection;

import com.luispiquinrey.product.Entities.Projection.BrandLookup;
import com.luispiquinrey.product.Event.BrandCreatedEvent;
import com.luispiquinrey.product.Event.BrandDeletedEvent;
import com.luispiquinrey.product.Event.BrandUpdatedEvent;
import com.luispiquinrey.product.Repository.RepositoryBrandLookup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@ProcessingGroup("brand-collection")
public class LookupBrandProjection {

    private final RepositoryBrandLookup repositoryBrandLookup;

    @EventHandler
    public void on(BrandCreatedEvent event) {
        log.info("Processing BrandCreatedEvent - ID: {}, Name: {}", event.getIdBrand(), event.getName());
        BrandLookup lookup = new BrandLookup();
        BeanUtils.copyProperties(event, lookup);
        repositoryBrandLookup.save(lookup);
        log.info("BrandLookup created successfully - ID: {}", event.getIdBrand());
    }

    @EventHandler
    public void on(BrandUpdatedEvent event) {
        log.info("Processing BrandUpdatedEvent - ID: {}, Name: {}", event.getIdBrand(), event.getName());
        repositoryBrandLookup.findById(event.getIdBrand()).ifPresentOrElse(
            lookup -> {
                BeanUtils.copyProperties(event, lookup);
                repositoryBrandLookup.save(lookup);
                log.info("BrandLookup updated successfully - ID: {}", event.getIdBrand());
            },
            () -> log.warn("BrandLookup not found for update - ID: {}", event.getIdBrand())
        );
    }

    @EventHandler
    public void on(BrandDeletedEvent event) {
        log.info("Processing BrandDeletedEvent - ID: {}", event.getIdBrand());
        repositoryBrandLookup.deleteById(event.getIdBrand());
        log.info("BrandLookup deleted successfully - ID: {}", event.getIdBrand());
    }
}
