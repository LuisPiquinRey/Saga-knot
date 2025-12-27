package com.luispiquinrey.product.Command;

import java.util.List;

import jakarta.validation.constraints.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.Enums.StatusProduct;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Entities.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private String idProduct;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 5, max = 100, message = "Product name must be between 5 and 100 characters")
    private String name;

    @NotNull(message = "Brand is required")
    private Brand brand;

    @PositiveOrZero(message = "Price must be zero or positive")
    private float price;

    @PositiveOrZero(message = "Stock must be zero or positive")
    private Integer stock;

    @NotEmpty(message = "At least one category must be assigned")
    private List<Category> categories;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Status is required")
    private StatusProduct status;
}