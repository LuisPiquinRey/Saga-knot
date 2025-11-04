package com.luispiquinrey.cart.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
public class CartDto {
    private String idCart;
    private String idUser;
    private float total;
    private int quantity;
    private List<CartItemDto> items;
}
