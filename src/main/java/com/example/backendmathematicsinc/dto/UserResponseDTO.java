package com.example.backendmathematicsinc.dto;

import com.example.backendmathematicsinc.model.User;
import com.example.backendmathematicsinc.model.UserRole;

public record UserResponseDTO(
        Long id,
        String username,
        UserRole role
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
