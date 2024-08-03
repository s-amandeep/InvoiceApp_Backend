package com.zionique.invoiceapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "variant")
//@Table(name = "variant", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"brand_id", "price", "description"})
//})
public class Variant extends BaseModel {

    private String description;

    @ManyToOne
    @JoinColumn(name = "price_option_id")
    private PriceOption priceOption;

}