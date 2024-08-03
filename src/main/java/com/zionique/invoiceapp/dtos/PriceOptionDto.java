package com.zionique.invoiceapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PriceOptionDto {

    private String price;
    private Set<VariantDto> variants;
}
