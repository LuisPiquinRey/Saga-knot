package com.luispiquinrey.product.Command;

import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.Enums.StatusProduct;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Entities.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProductCommand {
    @TargetAggregateIdentifier
    private String idProduct;
    private String name;
    private Brand brand;
    private float price;
    private Integer stock;
    private List<Category> categories;
    private Gender gender;
    private StatusProduct status;
}
