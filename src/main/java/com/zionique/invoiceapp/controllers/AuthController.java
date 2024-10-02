package com.zionique.invoiceapp.controllers;

import com.zionique.invoiceapp.dtos.AuthResponse;
import com.zionique.invoiceapp.dtos.LoginRequest;
import com.zionique.invoiceapp.dtos.SignupRequest;
import com.zionique.invoiceapp.jwt.JwtUtil;
import com.zionique.invoiceapp.models.Role;
import com.zionique.invoiceapp.models.RoleName;
import com.zionique.invoiceapp.models.User;
import com.zionique.invoiceapp.repositories.RoleRepository;
import com.zionique.invoiceapp.repositories.UserRepository;
import com.zionique.invoiceapp.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {

        if (userRepository.findByMobile(request.getMobile()).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse(false, "Mobile number already registered."));
        }
        System.out.println(request.getRole());
        User user = new User();
        user.setName(request.getName());
        user.setMobile(request.getMobile());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Assign roles
        String strRole = request.getRole();
        Role role;

        if (strRole.equals("ADMIN")) {
            role = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        } else {
            role = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        }

        user.setRole(role);

        userRepository.save(user);
        return ResponseEntity.ok(new AuthResponse(true, "User registered successfully!"));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String mobile = loginRequest.getMobile();
            String password = loginRequest.getPassword();

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mobile, password));

            User user = userRepository.findByMobile(mobile).orElseThrow(() -> new RuntimeException("User not found"));
            String token = jwtUtil.generateToken(mobile);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("name", user.getName());
            response.put("mobile", user.getMobile());
            response.put("role", user.getRole().getName());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(new AuthResponse(true, "Login successful", response));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new AuthResponse(false, "Invalid credentials"));
        }
    }
}