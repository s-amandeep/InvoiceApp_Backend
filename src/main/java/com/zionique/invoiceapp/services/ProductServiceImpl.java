package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.dtos.AddProductDto;
import com.zionique.invoiceapp.dtos.GetProductDto;
import com.zionique.invoiceapp.models.*;
import com.zionique.invoiceapp.repositories.PriceOptionRepository;
import com.zionique.invoiceapp.repositories.BrandRepository;
import com.zionique.invoiceapp.repositories.UnitOfMeasurementRepository;
import com.zionique.invoiceapp.repositories.VariantRepository;
import lombok.AllArgsConstructor;
//import org.springframework.security.core.parameters.P;
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
            variant.setTaxRate(addProductDto.getTaxRate());
            variant.setCessRate(addProductDto.getCessRate());
            variant.setHsnCode(addProductDto.getHsnCode());
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
//        if (variantRepository.existsById(id)) {
//            // Get the associated price option
//            variantRepository.deleteById(id);
//        } else {
//            throw new RuntimeException("Product not found");
//        }
        // Find the variant by ID
        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Get the associated price option
        PriceOption priceOption = variant.getPriceOption();

        // Delete the variant
        variantRepository.delete(variant);

        // Check if the price option has any other variants
        List<Variant> remainingVariants = variantRepository.findAllByPriceOption_Id(priceOption.getId());

        if (remainingVariants.isEmpty()) {
            // No other variants, delete the price option
            priceOptionRepository.delete(priceOption);

            // Check if the brand has any other price options
            Brand brand = priceOption.getBrand();
            List<PriceOption> remainingPriceOptions = priceOptionRepository.findAllByBrand_Id(brand.getId());

            if (remainingPriceOptions.isEmpty()) {
                // No other price options, delete the brand
                brandRepository.delete(brand);
            }
        }
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public List<PriceOption> getPricesForBrand(Long brandId) {
        return priceOptionRepository.findAllByBrand_Id(brandId);
    }

    @Override
    public List<Variant> getVariantsForPriceAndBrand(Long priceId) {
        return variantRepository.findAllByPriceOption_Id(priceId);
    }

    @Override
    public Variant getVariantById(Long id) {
        Optional<Variant> optionalVariant = variantRepository.findById(id);
        if (optionalVariant.isPresent()){
            return optionalVariant.get();
        } else {
            throw new RuntimeException("Variant not found");
        }
    }

    @Override
    public void updateVariantByIdAndStock(Long id, Double stock) {

        Optional<Variant> optionalVariant = variantRepository.findById(id);
        if (optionalVariant.isPresent()){
            Variant variant = optionalVariant.get();
            variant.setStock(stock);
            variantRepository.save(variant);
        }

//        variantRepository.updateVariantById(id);
    }
}
