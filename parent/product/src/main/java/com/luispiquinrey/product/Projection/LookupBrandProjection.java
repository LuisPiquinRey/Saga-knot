package com.luispiquinrey.product.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.BrandLookup;
import com.luispiquinrey.product.Event.BrandCreatedEvent;
import com.luispiquinrey.product.Repository.RepositoryBrandLookup;

@Component
@ProcessingGroup("brand-collection")
public class LookupBrandProjection {
    private final RepositoryBrandLookup repositoryBrandLookup;

    @Autowired
    public LookupBrandProjection(RepositoryBrandLookup repositoryBrandLookup) {
        this.repositoryBrandLookup = repositoryBrandLookup;
    }

    @EventHandler
    public void on(BrandCreatedEvent brandCreatedEvent){
        BrandLookup lookup = new BrandLookup();
        BeanUtils.copyProperties(brandCreatedEvent, lookup);
        repositoryBrandLookup.save(lookup);
    }
}
