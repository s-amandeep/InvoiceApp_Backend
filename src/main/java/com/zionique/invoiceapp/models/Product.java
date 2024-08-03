package com.zionique.invoiceapp.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class Product extends BaseModel{
    private String brandName;

    public Product() {
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PriceOption> priceOptions = new HashSet<>();

    // Helper method to manage bidirectional relationship
    public void addPriceOption(PriceOption priceOption) {
        if (priceOptions == null) {
            priceOptions = new HashSet<>();
        }
        priceOptions.add(priceOption);
        priceOption.setProduct(this);
    }

    public void removePriceOption(PriceOption priceOption) {
        if (priceOptions != null) {
            priceOptions.remove(priceOption);
            priceOption.setProduct(null);
        }
    }
}
