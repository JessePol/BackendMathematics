package com.example.backendmathematicsinc.dto;

import com.example.backendmathematicsinc.model.CartItem;
public record CartItemResponseDTO(
        Long productId,
        String productName,
        int quantity,
        double unitPrice,
        double itemTotal
) {
    public static CartItemResponseDTO fromEntity(CartItem cartItem) {
        if (cartItem == null || cartItem.getProduct() == null) {
            return null;
        }

        return new CartItemResponseDTO(
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice(),
                cartItem.getItemTotal()
        );
    }
}