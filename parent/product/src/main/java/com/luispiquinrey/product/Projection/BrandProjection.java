package com.luispiquinrey.product.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Event.BrandCreatedEvent;
import com.luispiquinrey.product.Event.BrandUpdatedEvent;
import com.luispiquinrey.product.Event.BrandDeletedEvent;
import com.luispiquinrey.product.Repository.RepositoryBrand;

@Component
@ProcessingGroup("brand-collection")
public class BrandProjection {

    private final RepositoryBrand repositoryBrand;

    @Autowired
    public BrandProjection(RepositoryBrand repositoryBrand) {
        this.repositoryBrand = repositoryBrand;
    }

    @EventHandler
    public void on(BrandCreatedEvent brandCreatedEvent){
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandCreatedEvent, brand);
        repositoryBrand.save(brand);
    }

    @EventHandler
    public void on(BrandUpdatedEvent brandUpdatedEvent){
        Brand brand = repositoryBrand.findById(brandUpdatedEvent.getIdBrand())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + brandUpdatedEvent.getIdBrand()));
        BeanUtils.copyProperties(brandUpdatedEvent, brand);
        repositoryBrand.save(brand);
    }

    @EventHandler
    public void on(BrandDeletedEvent brandDeletedEvent){
        repositoryBrand.deleteById(brandDeletedEvent.getIdBrand());
    }

    @ExceptionHandler(resultType=Exception.class)
    public void handle(Exception exception){
        System.err.println("General exception in BrandProjection: " + exception.getMessage());
    }

    @ExceptionHandler(resultType=IllegalArgumentException.class)
    public void on(IllegalArgumentException exception){
        System.err.println("IllegalArgumentException in BrandProjection: " + exception.getMessage());
    }
}
