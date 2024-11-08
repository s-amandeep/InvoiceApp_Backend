package com.zionique.invoiceapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantDto {

    private Long id;
    private String description;
    private Integer hsnCode;
    private Integer taxRate;
    private Integer cessRate;
    private Double stock;
    private String unitOfMeasurement;
}
