package com.luispiquinrey.product.DTO;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(@NotNull @NotBlank @Length(min=3 , max=20)String name,@NotNull @NotBlank @Length(min=3 , max=20)String brand,@PositiveOrZero float price,@PositiveOrZero Integer stock) {
    
}
