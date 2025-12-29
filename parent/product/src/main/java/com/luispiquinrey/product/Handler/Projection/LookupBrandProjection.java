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
        BrandLookup lookup = new BrandLookup();
        BeanUtils.copyProperties(event, lookup);
        repositoryBrandLookup.save(lookup);
    }

    @EventHandler
    public void on(BrandUpdatedEvent event) {
        repositoryBrandLookup.findById(event.getIdBrand()).ifPresent(lookup -> {
            BeanUtils.copyProperties(event, lookup);
            repositoryBrandLookup.save(lookup);
        });
    }

    @EventHandler
    public void on(BrandDeletedEvent event) {
        repositoryBrandLookup.deleteById(event.getIdBrand());
    }
}
