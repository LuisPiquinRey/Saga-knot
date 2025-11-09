package com.luispiquinrey.product.DTO;

import java.util.List;

import com.luispiquinrey.Enums.StatusProduct;

public record ProductDto(
        String name,
        String idBrand,
        List<String> categoryIds,
        String genderName,
        StatusProduct status,
        float price,
        Integer stock
) {
}