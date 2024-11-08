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

    public AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
