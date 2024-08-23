package com.zionique.invoiceapp.repositories;

import com.zionique.invoiceapp.models.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // Method to get all invoices sorted by invoiceDate in descending order
    @Query("SELECT i FROM Invoice i ORDER BY i.invoiceDate DESC")
    List<Invoice> findAllSortedByDate();

    Page<Invoice> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
