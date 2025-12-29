package com.luispiquinrey.product.Handler.Projection;

import com.luispiquinrey.product.Entities.Projection.CategoryLookup;
import com.luispiquinrey.product.Event.CategoryCreatedEvent;
import com.luispiquinrey.product.Event.CategoryDeletedEvent;
import com.luispiquinrey.product.Event.CategoryUpdatedEvent;
import com.luispiquinrey.product.Repository.RepositoryCategoryLookup;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("category-collection")
@RequiredArgsConstructor
public class LookupCategoryProjection {

    private final RepositoryCategoryLookup repositoryLookup;
    @EventHandler
    public void on(CategoryCreatedEvent event) {
        CategoryLookup lookup = new CategoryLookup();
        BeanUtils.copyProperties(event, lookup);
        repositoryLookup.save(lookup);
    }

    @EventHandler
    public void on(CategoryUpdatedEvent event) {
        repositoryLookup.findById(event.getIdCategory()).ifPresent(lookup -> {
            BeanUtils.copyProperties(event, lookup);
            repositoryLookup.save(lookup);
        });
    }

    @EventHandler
    public void on(CategoryDeletedEvent event) {
        repositoryLookup.deleteById(event.getIdCategory());
    }
}

