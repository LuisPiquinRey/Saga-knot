package com.luispiquinrey.cart.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddItemRequest {
    private String idProduct;
    private Integer quantity;
    private float pricePerUnit;
}
