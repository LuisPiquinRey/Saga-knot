package com.luispiquinrey.product.Event;

import java.util.List;

import com.luispiquinrey.common.Enums.StatusProduct;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Entities.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCreatedEvent {
    private String idProduct;
    private String name;
    private String idBrand;
    private float price;
    private Integer stock;
    private List<String> idCategories;
    private Gender gender;
    private StatusProduct status;
}
