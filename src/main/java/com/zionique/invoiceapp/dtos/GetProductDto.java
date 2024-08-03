package com.zionique.invoiceapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductDto {
    private String brandName;
    private String price;
    private Long variantId;
    private String description;
}
