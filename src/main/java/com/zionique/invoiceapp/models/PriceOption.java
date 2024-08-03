package com.zionique.invoiceapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "price_option")
public class PriceOption extends BaseModel {
    private String price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "priceOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Variant> variants = new HashSet<>();

    // Helper method to manage bidirectional relationship
    public void addVariant(Variant variant) {
        if (variants == null) {
            variants = new HashSet<>();
        }
        variants.add(variant);
        variant.setPriceOption(this);
    }

    public void removeVariant(Variant variant) {
        if (variants != null) {
            variants.remove(variant);
            variant.setPriceOption(null);
        }
    }
}