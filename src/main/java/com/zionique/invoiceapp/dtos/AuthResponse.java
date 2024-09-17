package com.zionique.invoiceapp.dtos;

import com.zionique.invoiceapp.models.Role;
import com.zionique.invoiceapp.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private boolean success;
    private String message;
    private Role role;
    private String name;
    private String mobile;
    private User user;

    public AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AuthResponse(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
//        this.role = role;
        this.user = user;
    }
}
