package com.luispiquinrey.Command;

import java.util.ArrayList;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.DTO.ItemCompact;
import com.luispiquinrey.Enums.StatusOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private String idOrder;
    private StatusOrder status;
    private float total;
    private int quantity;
    private String idProduct;
}
