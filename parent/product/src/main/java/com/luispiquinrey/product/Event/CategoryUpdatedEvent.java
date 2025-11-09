package com.luispiquinrey.product.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryUpdatedEvent {
    private String idCategory;
    private String name;
    private String description;
    private String image;
}
