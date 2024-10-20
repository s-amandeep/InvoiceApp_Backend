package com.zionique.invoiceapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";  // Typically, we include the token type (Bearer for JWT)
    private String mobile;
    private String name;
    private List<String> roles;

    public JwtResponse(String token, String mobile, String name, List<String> roles) {
        this.token = token;
        this.mobile = mobile;
        this.name = name;
        this.roles = roles;
    }
}

