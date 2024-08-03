package com.zionique.invoiceapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddProductDto {

    private String brandName;
    private BigDecimal price;
    private String description;
}
