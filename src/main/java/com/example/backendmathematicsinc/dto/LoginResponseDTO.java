package com.example.backendmathematicsinc.dto;


import com.example.backendmathematicsinc.model.User;

public record LoginResponseDTO(
        String token,
        String username
) {

    public static LoginResponseDTO fromEntityAndToken(User user, String token) {

        return new LoginResponseDTO(
                token,
                user.getUsername()
        );
    }
}