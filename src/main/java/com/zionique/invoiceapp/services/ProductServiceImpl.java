package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.dtos.GetProductDto;
import com.zionique.invoiceapp.models.PriceOption;
import com.zionique.invoiceapp.models.Product;
import com.zionique.invoiceapp.models.Variant;
import com.zionique.invoiceapp.repositories.PriceOptionRepository;
import com.zionique.invoiceapp.repositories.ProductRepository;
import com.zionique.invoiceapp.repositories.VariantRepository;
import lombok.AllArgsConstructor;
//import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private PriceOptionRepository priceOptionRepository;
    private VariantRepository variantRepository;

    @Override
    @Transactional
    public Product addNewProduct(Product product) {
        // Check if the product with the same brand name exists
        Optional<Product> existingProduct = productRepository.findByBrandName(product.getBrandName());

        Product savedProduct = existingProduct.orElseGet(() -> {
            // Save new product if it does not exist
            return productRepository.save(product);
        });

        // Process and save price options
        for (PriceOption priceOption : product.getPriceOptions()) {
            Optional<PriceOption> existingPriceOption = priceOptionRepository.findByPrice(priceOption.getPrice());

            PriceOption savedPriceOption = existingPriceOption.orElseGet(() -> {
                // Set the product reference before saving
                priceOption.setProduct(savedProduct);
                return priceOptionRepository.save(priceOption);
            });

            // Process and save variants
            for (Variant variant : priceOption.getVariants()) {
                // Check if the variant already exists
                // Assuming Variant has a unique identifier like description or a combination of attributes
                // Update this logic as needed based on your unique constraints
                Optional<Variant> existingVariant = variantRepository.findByDescriptionAndPriceOption(variant.getDescription(), savedPriceOption);

                Variant savedVariant = existingVariant.orElseGet(() -> {
                    // Link the variant to the price option
                    variant.setPriceOption(savedPriceOption);
                    return variantRepository.save(variant);
                });

                // Ensure the variant is linked to the price option
                if (!savedVariant.getPriceOption().equals(savedPriceOption)) {
                    savedVariant.setPriceOption(savedPriceOption);
                    variantRepository.save(savedVariant);
                }
            }
        }
        return savedProduct;
    }

    @Override
    public List<GetProductDto> getAllProducts() {
//        List<Variant> variants = variantRepository.findAll();
//        return variants.stream().map(variant -> {
//            GetProductDto dto = new GetProductDto();
//            dto.setBrandName(variant.getPriceOption().getProduct().getBrandName());
//            dto.setPrice(variant.getPriceOption().getPrice());
//            dto.setVariantId(variant.getId());
//            dto.setDescription(variant.getDescription());
//            return dto;
//        }).collect(Collectors.toList());
        return variantRepository.findAllSortedByBrandAndPrice();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.getProductById(id);
        return optionalProduct;
    }

//    @Override
//    public Optional<List<Product>> getProductsByCompany(String productCompany) {
//        Optional<List<Product>> optionalProducts = productRepository.getProductsByProductCompany(productCompany);
//        return optionalProducts;
//    }
//
//    @Override
//    public Optional<List<Product>> getProductsByCompanyAndMrp(String productCompany, Float mrp) {
//        Optional<List<Product>> optionalProducts = productRepository.getProductByProductCompanyAndMrp(productCompany, mrp);
//        return optionalProducts;
//    }
}
