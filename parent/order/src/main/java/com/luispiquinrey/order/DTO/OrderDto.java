package com.luispiquinrey.order.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.luispiquinrey.Enums.StatusOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String idOrder;
    private String idUser;
    private String idAddress;
    private LocalDateTime orderDate;
    private float subtotal;
    private float tax;
    private float shippingCost;
    private float total;
    private StatusOrder status;
    private String notes;
    private List<OrderItemDto> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
