package com.example.backendmathematicsinc.controller;

import com.example.backendmathematicsinc.dto.UpdateRoleRequestDTO;
import com.example.backendmathematicsinc.dto.UserResponseDTO;
import com.example.backendmathematicsinc.model.User;
import com.example.backendmathematicsinc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUserProfile() {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(UserResponseDTO.fromEntity(currentUser));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> usersDTO= users.stream()
                .map(UserResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long user_id) {
        return userService.getUserById(user_id)
                .map(user -> ResponseEntity.ok(UserResponseDTO.fromEntity(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{user_id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUserRole(@PathVariable Long user_id, @RequestBody UpdateRoleRequestDTO request) {
        User updatedUser = userService.updateUserRole(user_id, request.role());
        return ResponseEntity.ok(UserResponseDTO.fromEntity(updatedUser));
    }

    @DeleteMapping("/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long user_id) {
        userService.deleteUser(user_id);
        return ResponseEntity.noContent().build();
    }
}
