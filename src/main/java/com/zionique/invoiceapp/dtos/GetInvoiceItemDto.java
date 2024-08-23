package com.zionique.invoiceapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetInvoiceItemDto {
    private String brandName;
    private Double price;
    private String description;
    private Float quantity;
    private Double sellingPrice;
    private Double totalPrice;
}
