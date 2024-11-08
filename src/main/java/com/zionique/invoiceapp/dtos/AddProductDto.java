package com.zionique.invoiceapp.dtos;

import com.zionique.invoiceapp.models.UnitOfMeasurement;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddProductDto {

    private String brandName;
    private Double price;
    private String description;
    private Integer hsnCode;
    private Integer taxRate;
    private Integer cessRate;
    private String unitOfMeasurement;
    private Double stock;
}
