package com.example.backendmathematicsinc.controller;

import com.example.backendmathematicsinc.dto.CartResponseDTO;
import com.example.backendmathematicsinc.dto.request.AddToCartRequest;
import com.example.backendmathematicsinc.dto.request.UpdateQuantityRequest;
import com.example.backendmathematicsinc.model.Cart;
import com.example.backendmathematicsinc.model.User;
import com.example.backendmathematicsinc.service.CartService;
import com.example.backendmathematicsinc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getMyCart() {
        User currentUser = userService.getCurrentUser();
        Cart cart = cartService.getCartByUserId(currentUser.getId());
        return ResponseEntity.ok(CartResponseDTO.fromEntity(cart));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItemToMyCart(@RequestBody AddToCartRequest request) {
        User currentUser = userService.getCurrentUser();
        Cart updatedCart = cartService.addProductToCart(currentUser.getId(), request.productId(), request.quantity());
        return ResponseEntity.ok(CartResponseDTO.fromEntity(updatedCart));
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> updateItemQuantityInMyCart(@PathVariable Long productId, @RequestBody UpdateQuantityRequest request) {
        User currentUser = userService.getCurrentUser();
        Cart updatedCart = cartService.updateItemQuantity(currentUser.getId(), productId, request.quantity());
        return ResponseEntity.ok(CartResponseDTO.fromEntity(updatedCart));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeItemFromMyCart(@PathVariable Long productId) {
        User currentUser = userService.getCurrentUser();
        Cart updatedCart = cartService.removeProductFromCart(currentUser.getId(), productId);
        return ResponseEntity.ok(CartResponseDTO.fromEntity(updatedCart));
    }

    @DeleteMapping
    public ResponseEntity<CartResponseDTO> clearMyCart() {
        User currentUser = userService.getCurrentUser();
        Cart clearedCart = cartService.clearCart(currentUser.getId());
        return ResponseEntity.ok(CartResponseDTO.fromEntity(clearedCart));
    }

}
