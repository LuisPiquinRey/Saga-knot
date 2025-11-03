package com.luispiquinrey.product.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.CategoryLookup;
import com.luispiquinrey.product.Event.CategoryCreatedEvent;
import com.luispiquinrey.product.Repository.RepositoryCategoryLookup;

@Component
@ProcessingGroup("category-collection")
public class LookupCategoryProjection {
    private final RepositoryCategoryLookup repositoryCategoryLookup;

    @Autowired
    public LookupCategoryProjection(RepositoryCategoryLookup repositoryCategoryLookup) {
        this.repositoryCategoryLookup = repositoryCategoryLookup;
    }

    @EventHandler
    public void on(CategoryCreatedEvent categoryCreatedEvent){
        CategoryLookup lookup = new CategoryLookup();
        BeanUtils.copyProperties(categoryCreatedEvent, lookup);
        repositoryCategoryLookup.save(lookup);
    }
}
