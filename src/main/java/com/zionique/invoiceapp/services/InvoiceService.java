package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.models.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    Invoice addNewInvoice(Invoice invoice);

    List<Invoice> getAllInvoices();

    Optional<Invoice> getInvoiceById(Long id);

    void deleteInvoiceById(Long id);
}
