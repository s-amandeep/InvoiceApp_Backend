package com.zionique.invoiceapp.repositories;

import com.zionique.invoiceapp.models.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}
