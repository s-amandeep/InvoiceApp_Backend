package com.zionique.invoiceapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UnitOfMeasurement extends BaseModel{

    @Column(nullable = false, unique = true)
    private String name;
}
