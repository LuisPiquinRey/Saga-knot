package com.luispiquinrey.product.Command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBrandCommand {

    @TargetAggregateIdentifier
    private String idBrand;

    @NotBlank(message = "Brand name cannot be blank")
    @Size(min = 3, max = 100, message = "Brand name must be between 3 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}