package com.zionique.invoiceapp.repositories;

import com.zionique.invoiceapp.models.Brand;
import com.zionique.invoiceapp.models.PriceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceOptionRepository extends JpaRepository<PriceOption, Long> {
    @Query("SELECT p FROM PriceOption p WHERE p.brand = :brand AND p.price = :price")
    Optional<PriceOption> findByBrandAndPrice(Brand brand, Double price);
}
