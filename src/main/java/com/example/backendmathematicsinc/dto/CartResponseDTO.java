package com.example.backendmathematicsinc.dto;


import com.example.backendmathematicsinc.model.Cart;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record CartResponseDTO(
        Long id,
        Set<CartItemResponseDTO> items,
        double totalPrice
) {
    public static CartResponseDTO fromEntity(Cart cart) {
        if (cart == null) {
            return null;
        }

        Set<CartItemResponseDTO> itemsDTO = cart.getItems() == null ? Collections.emptySet() :
                cart.getItems().stream()
                        .map(CartItemResponseDTO::fromEntity)
                        .collect(Collectors.toSet());

        return new CartResponseDTO(cart.getId(), itemsDTO, cart.getTotalPrice());
    }
}