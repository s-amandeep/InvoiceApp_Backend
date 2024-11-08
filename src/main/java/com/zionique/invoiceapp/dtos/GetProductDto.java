package com.zionique.invoiceapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class GetProductDto {
    private String brandName;
    private Double price;
    private Long variantId;
    private String description;
    private Integer hsnCode;
    private Integer taxRate;
    private Integer cessRate;
    private Double stock;
    private String unitOfMeasurement;
}
