package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer addNewCustomer(Customer customer);

    Customer getCustomerFromId(Long id);

    List<Customer> getAllCustomers();
}
