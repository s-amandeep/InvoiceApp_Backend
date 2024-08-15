package com.zionique.invoiceapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "brand")
public class Brand extends BaseModel{

    @Column(nullable = false, unique = true)
    private String name;

    public Brand() {
    }
}
