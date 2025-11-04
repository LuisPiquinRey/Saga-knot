package com.luispiquinrey.order.DTO;

import com.luispiquinrey.Enums.StatusOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequest {
    private StatusOrder status;
}
