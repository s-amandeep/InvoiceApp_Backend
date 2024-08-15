package com.zionique.invoiceapp.repositories;

import com.zionique.invoiceapp.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
