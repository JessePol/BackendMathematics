package com.example.backendmathematicsinc.dto.request;

import com.example.backendmathematicsinc.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(
        @NotNull(message = "Status cannot be null")
        OrderStatus status
) {}
