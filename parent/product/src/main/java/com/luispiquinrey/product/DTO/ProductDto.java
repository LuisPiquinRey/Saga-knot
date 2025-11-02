package com.luispiquinrey.product.DTO;

import com.luispiquinrey.Enums.StatusProduct;

public record ProductDto(
        String name,
        StatusProduct status,
        float price,
        Integer stock
) {
}