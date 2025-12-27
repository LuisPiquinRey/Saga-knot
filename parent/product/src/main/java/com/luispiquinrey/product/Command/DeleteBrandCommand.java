package com.luispiquinrey.product.Command;

import jakarta.validation.constraints.NotBlank;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteBrandCommand {

    @TargetAggregateIdentifier
    private String idBrand;
}