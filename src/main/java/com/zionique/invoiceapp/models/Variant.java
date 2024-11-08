package com.zionique.invoiceapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
//@Table(name = "variant")
@Table(name = "variant", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand_id", "price", "description"})
})
public class Variant extends BaseModel {

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer hsnCode;

    @Column(nullable = false)
    private Integer taxRate;

    @Column(nullable = false)
    private Integer cessRate;

    @ManyToOne
    @JoinColumn(name = "price_option_id", nullable = false)
    private PriceOption priceOption;

    @Column(precision = 10, nullable = false)
    private Double stock;

    @ManyToOne
    @JoinColumn(name = "unit_of_measurement_id", nullable = false)
    private UnitOfMeasurement unitOfMeasurement;
}