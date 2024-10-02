package com.zionique.invoiceapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String name;

    @Column(unique = true)
    private String mobile;

    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
