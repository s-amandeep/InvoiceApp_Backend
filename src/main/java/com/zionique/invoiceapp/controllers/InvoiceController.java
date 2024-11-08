package com.zionique.invoiceapp.controllers;

import com.zionique.invoiceapp.dtos.AddInvoiceDto;
import com.zionique.invoiceapp.dtos.GetInvoiceDto;
import com.zionique.invoiceapp.dtos.GetInvoiceItemDto;
import com.zionique.invoiceapp.dtos.InvoiceItemDto;
import com.zionique.invoiceapp.models.Customer;
import com.zionique.invoiceapp.models.Invoice;
import com.zionique.invoiceapp.models.InvoiceItem;
import com.zionique.invoiceapp.models.Variant;
import com.zionique.invoiceapp.services.CustomerService;
import com.zionique.invoiceapp.services.InvoiceService;
import com.zionique.invoiceapp.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/invoices")
public class InvoiceController {
    private InvoiceService invoiceService;
    private CustomerService customerService;
    private ProductService productService;

    @PostMapping
    public ResponseEntity<GetInvoiceDto> createInvoice(@RequestBody AddInvoiceDto addInvoiceDto) {
        Invoice invoice = getInvoiceFromAddInvoiceDto(addInvoiceDto);
        Invoice createdInvoice = invoiceService.addNewInvoice(invoice);

        GetInvoiceDto getInvoiceDto = getGetInvoiceDtoFromInvoice(createdInvoice);

        return new ResponseEntity<>(getInvoiceDto, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    public ResponseEntity<Page<GetInvoiceDto>> getAllInvoices(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Page<Invoice> invoices = invoiceService.getAllInvoicesSortedByDate(page, size);
//        List<GetInvoiceDto> invoiceDtos = new ArrayList<>();
//        for (Invoice invoice: invoices){
//            GetInvoiceDto getInvoiceDto = getGetInvoiceDtoFromInvoice(invoice);
//            invoiceDtos.add(getInvoiceDto);
//        }
        return new ResponseEntity<>(invoices.map(this::getGetInvoiceDtoFromInvoice), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetInvoiceDto> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(id);
        if (optionalInvoice.isPresent()){
            Invoice invoice = optionalInvoice.get();
            GetInvoiceDto getInvoiceDto = getGetInvoiceDtoFromInvoice(invoice);
            return new ResponseEntity<>(getInvoiceDto, HttpStatus.OK);
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

    private GetInvoiceDto getGetInvoiceDtoFromInvoice(Invoice createdInvoice) {
        // Format the date
        LocalDate localDate = convertToLocalDate(createdInvoice.getInvoiceDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale.ENGLISH);
        String formattedDate = localDate.format(formatter);

        GetInvoiceDto getInvoiceDto = new GetInvoiceDto();
        getInvoiceDto.setInvoiceId(createdInvoice.getId());
        getInvoiceDto.setCustomerName(createdInvoice.getCustomer().getName());
        getInvoiceDto.setCustomerAddress(createdInvoice.getCustomer().getAddress());
        getInvoiceDto.setCustomerGstin(createdInvoice.getCustomer().getGstin());
        getInvoiceDto.setCustomerMobile(createdInvoice.getCustomer().getMobile());
        getInvoiceDto.setInvoiceDate(formattedDate);
        getInvoiceDto.setTotalTax(createdInvoice.getTotalTax());
        getInvoiceDto.setTotalValue(createdInvoice.getTotalValue());
        List<GetInvoiceItemDto> itemDtoList = new ArrayList<>();
        for (InvoiceItem invoiceItem: createdInvoice.getItems()){
            GetInvoiceItemDto invoiceItemDto = new GetInvoiceItemDto();
            invoiceItemDto.setBrandName(invoiceItem.getVariant().getPriceOption().getBrand().getName());
            invoiceItemDto.setPrice(invoiceItem.getVariant().getPriceOption().getPrice());
            invoiceItemDto.setDescription(invoiceItem.getVariant().getDescription());
            invoiceItemDto.setHsnCode(invoiceItem.getVariant().getHsnCode());
            invoiceItemDto.setUnitOfMeasurement(invoiceItem.getVariant().getUnitOfMeasurement().getName());
            invoiceItemDto.setTaxRate(invoiceItem.getVariant().getTaxRate());
            invoiceItemDto.setCessRate(invoiceItem.getVariant().getCessRate());
            invoiceItemDto.setQuantity(invoiceItem.getQuantity());
            invoiceItemDto.setSellingPrice(invoiceItem.getSellingPrice());
            invoiceItemDto.setTotalPrice(invoiceItem.getTotalPrice());
            itemDtoList.add((invoiceItemDto));
        }
        getInvoiceDto.setItemDtoList(itemDtoList);

        return getInvoiceDto;
    }
    private LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private Invoice getInvoiceFromAddInvoiceDto(AddInvoiceDto addInvoiceDto) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(addInvoiceDto.getInvoiceDate());
        Customer customer = customerService.getCustomerFromId(addInvoiceDto.getCustomerId());
        invoice.setCustomer(customer);
        invoice.setTotalValue(addInvoiceDto.getTotalValue());
        invoice.setTotalTax(addInvoiceDto.getTotalTax());
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
}
