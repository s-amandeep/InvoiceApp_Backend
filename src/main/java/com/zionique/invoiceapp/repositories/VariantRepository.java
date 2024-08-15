package com.zionique.invoiceapp.repositories;

import com.zionique.invoiceapp.dtos.GetProductDto;
import com.zionique.invoiceapp.models.PriceOption;
import com.zionique.invoiceapp.models.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VariantRepository extends JpaRepository<Variant, Long> {
    Optional<Variant> findByDescriptionAndPriceOption(String description, PriceOption priceOption);

    @Query("SELECT new com.zionique.invoiceapp.dtos.GetProductDto(v.priceOption.brand.name, v.priceOption.price, v.id, v.description," +
            "v.stock, v.unitOfMeasurement.name) " +
            "FROM Variant v " +
            "ORDER BY v.priceOption.brand.name ASC, v.priceOption.price ASC")
    List<GetProductDto> findAllSortedByBrandAndPrice();

    List<Variant> findAllByPriceOption_Id(Long id);

}
