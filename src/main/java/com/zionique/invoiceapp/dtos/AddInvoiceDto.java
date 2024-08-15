package com.zionique.invoiceapp.dtos;

import com.zionique.invoiceapp.models.InvoiceItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AddInvoiceDto {

    private Long customerId;
    private Date invoiceDate;
    private Double totalValue;
    private List<InvoiceItemDto> items = new ArrayList<>();
}
