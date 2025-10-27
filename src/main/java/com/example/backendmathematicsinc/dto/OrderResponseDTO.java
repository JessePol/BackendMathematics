package com.example.backendmathematicsinc.dto;


import com.example.backendmathematicsinc.model.Order;
import com.example.backendmathematicsinc.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record OrderResponseDTO(
        Long id,
        UserResponseDTO user,
        LocalDateTime orderDate,
        double totalPrice,
        OrderStatus status,
        Set<OrderItemResponseDTO> orderItems
) {
    public static OrderResponseDTO fromEntity(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                UserResponseDTO.fromEntity(order.getUser()),
                order.getOrderDate(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getOrderItems().stream()
                        .map(OrderItemResponseDTO::fromEntity)
                        .collect(Collectors.toSet())
        );
    }
}