package com.zionique.invoiceapp.repositories;

import com.zionique.invoiceapp.models.UnitOfMeasurement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitOfMeasurementRepository extends JpaRepository<UnitOfMeasurement, Long> {

    Optional<UnitOfMeasurement> findByName(String name);
}
