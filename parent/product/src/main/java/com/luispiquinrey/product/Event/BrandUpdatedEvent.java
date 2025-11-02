package com.luispiquinrey.product.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandUpdatedEvent {
    private String idBrand;
    private String name;
    private String description;
}
