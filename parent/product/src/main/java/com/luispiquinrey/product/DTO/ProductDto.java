package com.luispiquinrey.product.DTO;

import java.util.List;

import com.luispiquinrey.common.Enums.StatusProduct;

public record ProductDto(
        String name,
        String idBrand,
        List<String> idCategories,
        String genderName,
        StatusProduct status,
        float price,
        Integer stock
) {
}