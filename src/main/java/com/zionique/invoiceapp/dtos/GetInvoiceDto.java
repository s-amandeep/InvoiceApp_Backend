package com.zionique.invoiceapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GetInvoiceDto {

    private Long invoiceId;
    private String customerName;
    private String customerAddress;
    private String customerMobile;
    private String customerGstin;
    private String invoiceDate;
    private List<GetInvoiceItemDto> itemDtoList;
    private Double totalValue;
    private Double totalTax;
}
