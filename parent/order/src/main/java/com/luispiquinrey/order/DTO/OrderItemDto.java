package com.luispiquinrey.order.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private String idOrderItem;
    private String idProduct;
    private Integer quantity;
    private BigDecimal subtotal;
    private BigDecimal discount;
}
