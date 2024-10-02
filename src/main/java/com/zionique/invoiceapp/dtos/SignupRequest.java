package com.zionique.invoiceapp.dtos;

import com.zionique.invoiceapp.models.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    private String name;
    private String mobile;
    private String password;
    private String role;
}
