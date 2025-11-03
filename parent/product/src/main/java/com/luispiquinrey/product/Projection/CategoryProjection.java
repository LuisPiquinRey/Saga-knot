package com.luispiquinrey.product.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Event.CategoryCreatedEvent;
import com.luispiquinrey.product.Event.CategoryUpdatedEvent;
import com.luispiquinrey.product.Event.CategoryDeletedEvent;
import com.luispiquinrey.product.Repository.RepositoryCategory;

@Component
@ProcessingGroup("category-collection")
public class CategoryProjection {

    private final RepositoryCategory repositoryCategory;

    @Autowired
    public CategoryProjection(RepositoryCategory repositoryCategory) {
        this.repositoryCategory = repositoryCategory;
    }

    @EventHandler
    public void on(CategoryCreatedEvent categoryCreatedEvent){
        Category category = new Category();
        BeanUtils.copyProperties(categoryCreatedEvent, category);
        repositoryCategory.save(category);
    }

    @EventHandler
    public void on(CategoryUpdatedEvent categoryUpdatedEvent){
        Category category = repositoryCategory.findById(categoryUpdatedEvent.getIdCategory())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryUpdatedEvent.getIdCategory()));
        BeanUtils.copyProperties(categoryUpdatedEvent, category);
        repositoryCategory.save(category);
    }

    @EventHandler
    public void on(CategoryDeletedEvent categoryDeletedEvent){
        repositoryCategory.deleteById(categoryDeletedEvent.getIdCategory());
    }

    @ExceptionHandler(resultType=Exception.class)
    public void handle(Exception exception){
        System.err.println("General exception in CategoryProjection: " + exception.getMessage());
    }

    @ExceptionHandler(resultType=IllegalArgumentException.class)
    public void on(IllegalArgumentException exception){
        System.err.println("IllegalArgumentException in CategoryProjection: " + exception.getMessage());
    }
}
