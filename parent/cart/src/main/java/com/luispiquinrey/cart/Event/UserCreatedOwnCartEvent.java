package com.luispiquinrey.cart.Event;

import jakarta.validation.constraints.NotBlank;

public class UserCreatedOwnCartEvent {
    @NotBlank
    private String idUser;
}
