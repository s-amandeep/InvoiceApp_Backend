package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.models.User;
import com.zionique.invoiceapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public String signup(User user) {
        if (userRepository.findByMobile(user.getMobile()) != null) {
            return "Mobile number already registered.";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully.";
    }

    public User login(String mobile, String password) {
        User user = userRepository.findByMobile(mobile);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
