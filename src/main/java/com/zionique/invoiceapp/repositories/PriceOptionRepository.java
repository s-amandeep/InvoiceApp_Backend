package com.zionique.invoiceapp.repositories;

import com.zionique.invoiceapp.models.PriceOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceOptionRepository extends JpaRepository<PriceOption, Long> {
    Optional<PriceOption> findByPrice(String price);
}
