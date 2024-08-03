package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {

    Customer addNewCustomer(Customer customer);
    List<Customer> getAllCustomers();
}
