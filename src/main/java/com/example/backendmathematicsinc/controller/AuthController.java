package com.example.backendmathematicsinc.controller;

import com.example.backendmathematicsinc.dto.LoginResponseDTO;
import com.example.backendmathematicsinc.dto.UserResponseDTO;
import com.example.backendmathematicsinc.dto.request.AuthenticationRequest;
import com.example.backendmathematicsinc.model.User;
import com.example.backendmathematicsinc.model.UserRole;
import com.example.backendmathematicsinc.repository.UserRepository;
import com.example.backendmathematicsinc.util.JWTUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JWTUtils jwtUtils,
                          PasswordEncoder passwordEncoder,
                          UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.username(),
                        authRequest.password()
                )
        );
        User authenticatedUser = (User) authentication.getPrincipal();
        String token = jwtUtils.generateToken(authenticatedUser);
        LoginResponseDTO response = LoginResponseDTO.fromEntityAndToken(authenticatedUser, token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody AuthenticationRequest authRequest) {
        User newUser = new User();
        newUser.setUsername(authRequest.username());
        newUser.setPassword(passwordEncoder.encode(authRequest.password()));
        newUser.setRole(UserRole.ROLE_USER);
        userRepository.save(newUser);

        return ResponseEntity
                .status(201)
                .body(UserResponseDTO.fromEntity(newUser));
    }
}
