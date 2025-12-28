package com.luispiquinrey.user.Command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadImageUserCommand {
    @TargetAggregateIdentifier
    private String username;
    @NotBlank(message = "Key is required")
    @Size(max = 255, message = "Key must be at most 255 characters")
    private String key;

    @NotBlank(message = "Image URL is required")
    @Pattern(regexp = "^(https?://).+", message = "Image URL must be a valid http or https URL")
    private String imageUrl;
}
