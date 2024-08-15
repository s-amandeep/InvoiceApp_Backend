package com.zionique.invoiceapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceItemDto {
    private Long variantId;
    private Float quantity;
    private Double sellingPrice;
    private Double totalPrice;
}
