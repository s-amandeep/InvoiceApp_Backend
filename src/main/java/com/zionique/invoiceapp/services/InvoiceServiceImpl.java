package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.models.Invoice;
import com.zionique.invoiceapp.models.InvoiceItem;
import com.zionique.invoiceapp.models.Variant;
import com.zionique.invoiceapp.repositories.InvoiceItemRepository;
import com.zionique.invoiceapp.repositories.InvoiceRepository;
import com.zionique.invoiceapp.repositories.VariantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{
    private InvoiceRepository invoiceRepository;
    private InvoiceItemRepository invoiceItemRepository;

    @Override
    @Transactional
    public Invoice addNewInvoice(Invoice invoice) {
        // Save the invoice first
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // Set the saved invoice to each item
        for (InvoiceItem item : invoice.getItems()) {
            item.setInvoice(savedInvoice);
        }

        // Save the invoice items
        invoiceItemRepository.saveAll(invoice.getItems());

        return savedInvoice;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public void deleteInvoiceById(Long id) {
        Optional<Invoice> invoice = getInvoiceById(id);
        if (invoice.isPresent()){
            invoiceRepository.delete(invoice.get());
        } else {
            throw new RuntimeException("Invoice does not exist");
        }
    }
}
