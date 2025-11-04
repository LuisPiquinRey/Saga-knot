package com.luispiquinrey.user.Command;

import java.util.HashMap;

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
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private HashMap<String,Address> addresses;
}
