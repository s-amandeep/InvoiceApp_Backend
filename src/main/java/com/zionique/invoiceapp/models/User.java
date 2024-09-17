package com.zionique.invoiceapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String name;

    @Column(unique = true)
    private String mobile;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
