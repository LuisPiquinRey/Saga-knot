package com.luispiquinrey.order.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddItemRequest {
    private String idProduct;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal discount;
}
