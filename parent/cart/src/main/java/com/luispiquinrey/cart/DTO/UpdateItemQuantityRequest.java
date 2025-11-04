package com.luispiquinrey.cart.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateItemQuantityRequest {
    private Integer newQuantity;
}
