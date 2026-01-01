package com.luispiquinrey.product.Command;

import java.util.List;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.common.Enums.StatusProduct;
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

    @Size(min = 5, max = 100, message = "Product name must be between 5 and 100 characters")
    private String name;

    @PositiveOrZero(message = "Price must be zero or positive")
    private float price;

    @PositiveOrZero(message = "Stock must be zero or positive")
    private Integer stock;

    private Gender gender;

    private StatusProduct status;
}