package com.zionique.invoiceapp.controllers;

import com.zionique.invoiceapp.dtos.AuthResponse;
import com.zionique.invoiceapp.dtos.LoginRequest;
import com.zionique.invoiceapp.models.User;
import com.zionique.invoiceapp.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;

    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        String message = authService.signup(user);
        if (message.equals("User registered successfully.")) {
            return ResponseEntity.ok(new AuthResponse(true, message));
        } else {
            return ResponseEntity.badRequest().body(new AuthResponse(false, message));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = authService.login(loginRequest.getMobile(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(new AuthResponse(true, "Login successful", user));
        } else {
            return ResponseEntity.status(401).body(new AuthResponse(false, "Invalid credentials"));
        }
    }
}

