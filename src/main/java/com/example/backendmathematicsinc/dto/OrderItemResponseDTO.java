package com.example.backendmathematicsinc.dto;

import com.example.backendmathematicsinc.model.OrderItem;

public record OrderItemResponseDTO(
        Long id,
        Long productId,
        String productName,
        double priceAtPurchase,
        int quantity,
        double totalPrice
) {
    public static OrderItemResponseDTO fromEntity(OrderItem orderItem) {
        return new OrderItemResponseDTO(
                orderItem.getId(),
                orderItem.getProductId(),
                orderItem.getProductName(),
                orderItem.getPriceAtPurchase(),
                orderItem.getQuantity(),
                orderItem.getItemTotal()
        );
    }
}
