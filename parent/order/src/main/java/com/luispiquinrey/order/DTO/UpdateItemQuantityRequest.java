package com.luispiquinrey.order.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemQuantityRequest {
    private String idProduct;
    private Integer newQuantity;
}
