package com.zionique.invoiceapp.dtos;

import com.zionique.invoiceapp.models.UnitOfMeasurement;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetInvoiceItemDto {
    private String brandName;
    private Double price;
    private String description;
    private Double quantity;
    private Double sellingPrice;
    private Double totalPrice;

    private Integer hsnCode;
    private Integer taxRate;
    private Integer cessRate;
    private String unitOfMeasurement;
}
