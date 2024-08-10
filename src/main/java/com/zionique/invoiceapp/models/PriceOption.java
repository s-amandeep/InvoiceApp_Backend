package com.zionique.invoiceapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "price_option", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand_id", "price"})
})
public class PriceOption extends BaseModel {
    @Column(precision = 10, nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

}