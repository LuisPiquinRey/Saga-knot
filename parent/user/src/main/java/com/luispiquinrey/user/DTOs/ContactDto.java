package com.luispiquinrey.user.DTOs;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactDto(
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        @NotBlank
        @NonNull
        String username,
        @Email(message = "Email format is invalid")
        @NotBlank(message = "Email is required")
        @NonNull
        String email,
        @NotBlank
        @NonNull
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
                message = "Password must be at least 8 characters with uppercase, lowercase, digit and special character")
        String password,
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$",
                message = "Phone number must be a valid international format")
        String phoneNumber
        ) {

    @Override
    public String toString() {
        return "📦 RequestContactDTO {\n"
                + "   👤 Username: '" + username + "',\n"
                + "   📧 Email: '" + email + "',\n"
                + "   🔒 Password: '********',\n"
                + "   📱 Phone: '" + phoneNumber + "'\n"
                + "}";
    }
}
