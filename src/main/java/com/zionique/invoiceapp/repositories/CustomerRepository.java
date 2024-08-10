package com.zionique.invoiceapp.repositories;

import com.zionique.invoiceapp.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findById(Long id);

    List<Customer> findAll();
}
