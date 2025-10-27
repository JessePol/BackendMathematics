package com.example.backendmathematicsinc.dto;


import com.example.backendmathematicsinc.model.User;
import com.example.backendmathematicsinc.model.UserRole;

public record LoginResponseDTO(
        String token,
        String username,
        UserRole userRole
) {

    public static LoginResponseDTO fromEntityAndToken(User user, String token) {

        return new LoginResponseDTO(
                token,
                user.getUsername(),
                user.getRole()
        );
    }
}