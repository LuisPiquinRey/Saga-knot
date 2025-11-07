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
    private String name;
    private String description;
}
