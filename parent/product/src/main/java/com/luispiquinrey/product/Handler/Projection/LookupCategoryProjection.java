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
import lombok.extern.slf4j.Slf4j;
@Component
@ProcessingGroup("category-collection")
@RequiredArgsConstructor
@Slf4j
public class LookupCategoryProjection {

    private final RepositoryCategoryLookup repositoryLookup;

    @EventHandler
    public void on(CategoryCreatedEvent event) {
        log.info("Processing CategoryCreatedEvent - ID: {}, Name: {}, Image: {}",
                event.getIdCategory(), event.getName(), event.getImage());
        CategoryLookup lookup = new CategoryLookup();
        BeanUtils.copyProperties(event, lookup);
        repositoryLookup.save(lookup);
        log.info("CategoryLookup created successfully - ID: {}", event.getIdCategory());
    }

    @EventHandler
    public void on(CategoryUpdatedEvent event) {
        log.info("Processing CategoryUpdatedEvent - ID: {}, Name: {}", event.getIdCategory(), event.getName());
        repositoryLookup.findById(event.getIdCategory()).ifPresentOrElse(
            lookup -> {
                BeanUtils.copyProperties(event, lookup);
                repositoryLookup.save(lookup);
                log.info("CategoryLookup updated successfully - ID: {}", event.getIdCategory());
            },
            () -> log.warn("CategoryLookup not found for update - ID: {}", event.getIdCategory())
        );
    }

    @EventHandler
    public void on(CategoryDeletedEvent event) {
        log.info("Processing CategoryDeletedEvent - ID: {}", event.getIdCategory());
        repositoryLookup.deleteById(event.getIdCategory());
        log.info("CategoryLookup deleted successfully - ID: {}", event.getIdCategory());
    }
}

