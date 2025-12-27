package com.luispiquinrey.product.Command;

import jakarta.validation.constraints.Size;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateBrandCommand {

    @TargetAggregateIdentifier
    private String idBrand;

    @Size(min = 3, max = 100, message = "Brand name must be between 3 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}