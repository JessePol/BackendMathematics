package com.example.backendmathematicsinc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthenticationRequest(
        @NotBlank(message = "Username is required.")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters.")
        @Pattern(regexp = "^[a-zA-Z0-9_\\-]+$",
                message = "Username can only contain letters, numbers, hyphens, and underscores.")
        String username,

        @NotBlank(message = "Password is required.")
        String password
){}
