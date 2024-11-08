package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.models.Invoice;
import com.zionique.invoiceapp.models.InvoiceItem;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    Invoice addNewInvoice(Invoice invoice);

//    void updateVariantById(Long id);

    Page<Invoice> getAllInvoicesSortedByDate(int page, int size);

    Optional<Invoice> getInvoiceById(Long id);

    void deleteInvoiceById(Long id);
}
