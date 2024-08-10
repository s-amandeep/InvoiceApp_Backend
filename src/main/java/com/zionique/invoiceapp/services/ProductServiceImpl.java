package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.dtos.AddProductDto;
import com.zionique.invoiceapp.dtos.GetProductDto;
import com.zionique.invoiceapp.models.PriceOption;
import com.zionique.invoiceapp.models.Brand;
import com.zionique.invoiceapp.models.UnitOfMeasurement;
import com.zionique.invoiceapp.models.Variant;
import com.zionique.invoiceapp.repositories.PriceOptionRepository;
import com.zionique.invoiceapp.repositories.BrandRepository;
import com.zionique.invoiceapp.repositories.UnitOfMeasurementRepository;
import com.zionique.invoiceapp.repositories.VariantRepository;
import lombok.AllArgsConstructor;
//import org.springframework.security.core.parameters.P;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private BrandRepository brandRepository;
    private PriceOptionRepository priceOptionRepository;
    private VariantRepository variantRepository;
    private UnitOfMeasurementRepository unitOfMeasurementRepository;

    @Override
    @Transactional
    public Variant addNewProduct(AddProductDto addProductDto) {
        // Check if the product with the same brand name exists
        Brand brand = brandRepository.findByName(addProductDto.getBrandName())
                .orElseGet(() -> {
                    Brand newBrand = new Brand();
                    newBrand.setName(addProductDto.getBrandName());
                    return brandRepository.save(newBrand);
                });

        PriceOption priceOption = priceOptionRepository.findByBrandAndPrice(brand, addProductDto.getPrice())
                .orElseGet(() -> {
                    PriceOption newPriceOption = new PriceOption();
                    newPriceOption.setBrand(brand);
                    newPriceOption.setPrice(addProductDto.getPrice());
                    return priceOptionRepository.save(newPriceOption);
                });

        UnitOfMeasurement unitOfMeasurement = unitOfMeasurementRepository.findByName(addProductDto.getUnitOfMeasurement())
                .orElseGet(() -> {
                    UnitOfMeasurement newUnit = new UnitOfMeasurement();
                    newUnit.setName(addProductDto.getUnitOfMeasurement());
                    return unitOfMeasurementRepository.save(newUnit);
                });

        Optional<Variant> optionalVariant = variantRepository.findByDescriptionAndPriceOption(addProductDto.getDescription(), priceOption);

        Variant variant;
        if (optionalVariant.isEmpty()) {
            variant = new Variant();
            variant.setPriceOption(priceOption);
            variant.setDescription(addProductDto.getDescription());
            variant.setStock(addProductDto.getStock());
            variant.setUnitOfMeasurement(unitOfMeasurement);
            variantRepository.save(variant);
        } else
            variant = optionalVariant.get();

        return variant;
    }

    @Override
    public List<GetProductDto> getAllProducts() {
        return variantRepository.findAllSortedByBrandAndPrice();
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {
        if (variantRepository.existsById(id)) {
            variantRepository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found");
        }
    }
}
