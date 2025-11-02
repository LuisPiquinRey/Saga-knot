package com.luispiquinrey.product.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    String name,

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    String description,

    @Size(max = 255, message = "Image URL cannot exceed 255 characters")
    String image
) {}
