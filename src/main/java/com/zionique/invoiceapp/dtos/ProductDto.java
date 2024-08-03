package com.zionique.invoiceapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductDto {
    private String brandName;

    private Set<PriceOptionDto> priceOptions;
}
