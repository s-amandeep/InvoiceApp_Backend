package com.zionique.invoiceapp.repositories;

import com.zionique.invoiceapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findByBrandName(String brandName);

//    Optional<List<Product>> getProductByProductCompanyAndMrp(String productCompany, Float mrp);

    Optional<Product> getProductById(Long id);
}
