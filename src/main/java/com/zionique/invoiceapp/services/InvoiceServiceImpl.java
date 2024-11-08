package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.models.Invoice;
import com.zionique.invoiceapp.models.InvoiceItem;
import com.zionique.invoiceapp.models.Variant;
import com.zionique.invoiceapp.repositories.InvoiceItemRepository;
import com.zionique.invoiceapp.repositories.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{
    private InvoiceRepository invoiceRepository;
    private InvoiceItemRepository invoiceItemRepository;
    private ProductService productService;

    @Override
    @Transactional
    public Invoice addNewInvoice(Invoice invoice) {
        // Check if each product has enough stock
        for (InvoiceItem item : invoice.getItems()) {
            Variant variant = item.getVariant();
            Double stockRemaining = variant.getStock() - item.getQuantity();
            if (stockRemaining < 0) {
                throw new RuntimeException("Not enough stock for product: " + variant.getDescription());
            }
        }

        // Save the invoice first
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // Set the saved invoice to each item
        for (InvoiceItem item : savedInvoice.getItems()) {
            Variant variant = item.getVariant();
            Double stockRemaining = variant.getStock() - item.getQuantity();
            productService.updateVariantByIdAndStock(variant.getId(), stockRemaining);
            item.setInvoice(savedInvoice);
        }

        // Save the invoice items
        invoiceItemRepository.saveAll(invoice.getItems());

        return savedInvoice;
    }

//    @Override
//    public void updateVariantById(Long id) {
//        productService.updateVariantById(id);
//    }

    @Override
    public Page<Invoice> getAllInvoicesSortedByDate(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return invoiceRepository.findAllByOrderByCreatedAtDesc(pageRequest);
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
