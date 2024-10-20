package com.zionique.invoiceapp.controllers;

import com.zionique.invoiceapp.dtos.CustomerDto;
import com.zionique.invoiceapp.models.Customer;
import com.zionique.invoiceapp.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/customers")
public class CustomerController {

    private CustomerService customerService;

    @PostMapping
    private ResponseEntity<Customer> addCustomer(@RequestBody CustomerDto customerDto){
        Customer customer = customerService.addNewCustomer(getCustomerFromCustomerDto(customerDto));
        ResponseEntity<Customer> response = new ResponseEntity<>(customer, HttpStatus.OK);
        return response;
    }

    @GetMapping
    private ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    private Customer getCustomerFromCustomerDto(CustomerDto customerDto) {
        Customer customer = Customer.builder()
                .name(customerDto.getName())
                .address(customerDto.getAddress())
                .mobile(customerDto.getMobile())
                .build();
        return customer;
    }
}
