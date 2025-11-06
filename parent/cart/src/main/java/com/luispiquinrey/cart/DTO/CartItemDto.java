package com.luispiquinrey.cart.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDto {
    private String idItem;
    private String idProduct;
    private int quantity;
    private float total;
    private float pricePerUnit;
}
