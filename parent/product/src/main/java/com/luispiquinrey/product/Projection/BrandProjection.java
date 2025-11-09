package com.luispiquinrey.product.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Event.BrandCreatedEvent;
import com.luispiquinrey.product.Event.BrandDeletedEvent;
import com.luispiquinrey.product.Event.BrandUpdatedEvent;
import com.luispiquinrey.product.Service.BrandService;


@Component
@ProcessingGroup("brand-collection")
public class BrandProjection {

    private final BrandService brandService;

    @Autowired
    public BrandProjection(BrandService brandService) {
        this.brandService = brandService;
    }

    @EventHandler
    public void on(BrandCreatedEvent event) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(event, brand);
        brandService.createTarget(brand);
    }

    @EventHandler
    public void on(BrandUpdatedEvent event) {
        brandService.findTargetById(event.getIdBrand()).ifPresent(brand -> {
            brand.setName(event.getName());
            brand.setDescription(event.getDescription());
            brandService.createTarget(brand);
        });
    }

    @EventHandler
    public void on(BrandDeletedEvent event) {
        brandService.deleteTarget(event.getIdBrand());
    }
}
