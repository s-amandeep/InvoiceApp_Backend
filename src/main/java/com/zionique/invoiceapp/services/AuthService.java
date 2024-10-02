package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.jwt.JwtUtil;
import com.zionique.invoiceapp.models.User;
import com.zionique.invoiceapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {


    private UserRepository userRepository;


    private JwtUtil jwtUtil;
//    private PasswordEncoder passwordEncoder;

    public String signup(User user) {
        if (userRepository.findByMobile(user.getMobile()).isPresent()) {
            return "Mobile number already registered.";
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully.";
    }

    public User login(String mobile, String password) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mobile, password));

        User user = userRepository.findByMobile(mobile).orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtUtil.generateToken(mobile);
        return null;
    }
}
