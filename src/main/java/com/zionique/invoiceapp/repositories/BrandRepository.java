package com.zionique.invoiceapp.repositories;

import com.zionique.invoiceapp.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand save(Brand brand);

    List<Brand> findAll();

    Optional<Brand> findByName(String name);

    Optional<Brand> getProductById(Long id);
}
