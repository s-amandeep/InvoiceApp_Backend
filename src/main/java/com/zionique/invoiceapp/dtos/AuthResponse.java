package com.zionique.invoiceapp.dtos;

import com.zionique.invoiceapp.models.Role;
import com.zionique.invoiceapp.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AuthResponse {
    private boolean success;
    private String message;
//    private User user;
    Map<String, Object> response;
//    private String name;
//    private String mobile;

    public AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AuthResponse(boolean success, String message, Map<String, Object> response) {
        this.success = success;
        this.message = message;
//        this.role = role;
//        this.user = user;
        this.response = response;
    }
}
