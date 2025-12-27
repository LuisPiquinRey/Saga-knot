package com.luispiquinrey.product.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCategoryCommand {

    @TargetAggregateIdentifier
    private String idCategory;

    @Size(min = 3, max = 100, message = "Category name must be between 3 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Size(max = 200, message = "Image URL cannot exceed 200 characters")
    private String image;
}