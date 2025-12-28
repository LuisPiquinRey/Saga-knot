package com.luispiquinrey.user.Command;

import java.util.HashMap;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.user.Entities.Address;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 8, max = 100, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).*$",
            message = "Password must include uppercase, lowercase, digits and a special character")
    private String password;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be in a valid international format")
    private String phoneNumber;

    @Valid
    private HashMap<String, Address> addresses;
}