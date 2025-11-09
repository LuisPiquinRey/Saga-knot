package com.luispiquinrey.product.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Event.CategoryCreatedEvent;
import com.luispiquinrey.product.Event.CategoryDeletedEvent;
import com.luispiquinrey.product.Event.CategoryUpdatedEvent;
import com.luispiquinrey.product.Service.CategoryService;

@Component
@ProcessingGroup("category-collection")
public class CategoryProjection {

    private final CategoryService categoryService;

    @Autowired
    public CategoryProjection(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @EventHandler
    public void on(CategoryCreatedEvent event) {
        Category category = new Category();
        BeanUtils.copyProperties(event, category);
        categoryService.createTarget(category);
    }

    @EventHandler
    public void on(CategoryUpdatedEvent event) {
        categoryService.findTargetById(event.getIdCategory()).ifPresent(category -> {
            category.setName(event.getName());
            category.setDescription(event.getDescription());
            category.setImage(event.getImage());
            categoryService.createTarget(category);
        });
    }

    @EventHandler
    public void on(CategoryDeletedEvent event) {
        categoryService.deleteTarget(event.getIdCategory());
    }

}
