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

//    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<PriceOption> priceOptions = new HashSet<>();
//
//    // Helper method to manage bidirectional relationship
//    public void addPriceOption(PriceOption priceOption) {
//        if (priceOptions == null) {
//            priceOptions = new HashSet<>();
//        }
//        priceOptions.add(priceOption);
//        priceOption.setBrand(this);
//    }
//
//    public void removePriceOption(PriceOption priceOption) {
//        if (priceOptions != null) {
//            priceOptions.remove(priceOption);
//            priceOption.setBrand(null);
//        }
//    }
}
