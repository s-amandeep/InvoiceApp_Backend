package com.zionique.invoiceapp.controllers;

import com.zionique.invoiceapp.dtos.AddInvoiceDto;
import com.zionique.invoiceapp.dtos.InvoiceItemDto;
import com.zionique.invoiceapp.models.Customer;
import com.zionique.invoiceapp.models.Invoice;
import com.zionique.invoiceapp.models.InvoiceItem;
import com.zionique.invoiceapp.models.Variant;
import com.zionique.invoiceapp.services.CustomerService;
import com.zionique.invoiceapp.services.InvoiceService;
import com.zionique.invoiceapp.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/invoices")
public class InvoiceController {
    private InvoiceService invoiceService;
    private CustomerService customerService;
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createInvoice(@RequestBody AddInvoiceDto addInvoiceDto) {
        Invoice invoice = getInvoiceFromAddInvoiceDto(addInvoiceDto);
        Invoice createdInvoice = invoiceService.addNewInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).build();
//        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    private Invoice getInvoiceFromAddInvoiceDto(AddInvoiceDto addInvoiceDto) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(addInvoiceDto.getInvoiceDate());
        Customer customer = customerService.getCustomerFromId(addInvoiceDto.getCustomerId());
        invoice.setCustomer(customer);
        invoice.setTotalValue(addInvoiceDto.getTotalValue());
        List<InvoiceItem> items = new ArrayList<>();
        for (InvoiceItemDto invoiceItemDto: addInvoiceDto.getItems()){
            InvoiceItem invoiceItem = getInvoiceItemFromInvoiceItemDto(invoiceItemDto);
            items.add(invoiceItem);
        }
        invoice.setItems(items);
        return invoice;
    }

    private InvoiceItem getInvoiceItemFromInvoiceItemDto(InvoiceItemDto invoiceItemDto) {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setQuantity(invoiceItemDto.getQuantity());
        invoiceItem.setSellingPrice(invoiceItemDto.getSellingPrice());
        invoiceItem.setTotalPrice(invoiceItemDto.getTotalPrice());
        Variant variant = productService.getVariantById(invoiceItemDto.getVariantId());
        invoiceItem.setVariant(variant);
        return  invoiceItem;
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(id);
        if (optionalInvoice.isPresent()){
            Invoice invoice = optionalInvoice.get();
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoiceById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
