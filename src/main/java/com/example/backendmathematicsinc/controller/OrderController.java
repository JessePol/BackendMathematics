package com.example.backendmathematicsinc.controller;

import com.example.backendmathematicsinc.dto.OrderResponseDTO;
import com.example.backendmathematicsinc.dto.request.UpdateOrderStatusRequest;
import com.example.backendmathematicsinc.model.Order;
import com.example.backendmathematicsinc.model.User;
import com.example.backendmathematicsinc.service.OrderService;
import com.example.backendmathematicsinc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<OrderResponseDTO> createMyOrder() {
        User currentUser = userService.getCurrentUser();
        Order newOrder = orderService.createOrderFromCart(currentUser.getId());
        return new ResponseEntity<>(OrderResponseDTO.fromEntity(newOrder), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders() {
        User currentUser = userService.getCurrentUser();
        List<Order> orders = orderService.getOrdersForUser(currentUser.getId());
        List<OrderResponseDTO> orderDTOs= orders.stream()
                .map(OrderResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponseDTO> orderDTOs = orders.stream()
                .map(OrderResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId)
                .map(order -> ResponseEntity.ok(OrderResponseDTO.fromEntity(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request
    ) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, request.status());
        return ResponseEntity.ok(OrderResponseDTO.fromEntity(updatedOrder));
    }
}