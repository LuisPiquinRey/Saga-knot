package com.luispiquinrey.product.Handler.Command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.DisallowReplay;
import org.axonframework.eventhandling.EventHandler;
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
@DisallowReplay
public class BrandCommandHandler {

    private final BrandService brandService;

    @Autowired
    public BrandCommandHandler(BrandService brandService) {
        this.brandService = brandService;
    }

    @EventHandler
    public void on(BrandCreatedEvent event) {
        Brand brand = new Brand();
        brand.setId(event.getIdBrand());
        BeanUtils.copyProperties(event, brand);
        brandService.createTarget(brand);
    }
    @EventHandler
    public void on(BrandUpdatedEvent event) {
        Brand brand = brandService.findTargetById(event.getIdBrand())
                .orElseThrow(() -> new IllegalStateException(
                        "Brand with ID " + event.getIdBrand() + " does not exist in read model"));

        brand.setName(event.getName());
        brand.setDescription(event.getDescription());

        brandService.updateTarget(brand);
    }
    @EventHandler
    public void on(BrandDeletedEvent event) {
        brandService.deleteTarget(event.getIdBrand());
    }
}
