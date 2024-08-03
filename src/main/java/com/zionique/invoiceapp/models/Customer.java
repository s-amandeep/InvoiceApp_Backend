package com.zionique.invoiceapp.models;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Customer extends BaseModel{

    private String name;
    private String address;
    private String mobile;

    public Customer() {

    }
}
