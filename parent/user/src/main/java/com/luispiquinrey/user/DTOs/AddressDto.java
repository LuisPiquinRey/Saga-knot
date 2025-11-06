package com.luispiquinrey.user.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressDto(
    @NotBlank(message = "Street cannot be blank")
    @Size(max = 100, message = "Street must not exceed 100 characters")
    String street,

    @Size(max = 10, message = "Postal code must not exceed 10 characters")
    @Pattern(regexp = "^[A-Za-z0-9\\- ]*$", message = "Postal code can only contain letters, numbers, and hyphens")
    String postalCode,

    @NotBlank(message = "City cannot be blank")
    @Size(max = 50, message = "City must not exceed 50 characters")
    String city,

    @Size(max = 50, message = "State must not exceed 50 characters")
    String state,

    @NotBlank(message = "Country cannot be blank")
    @Size(max = 50, message = "Country must not exceed 50 characters")
    String country
) {}

