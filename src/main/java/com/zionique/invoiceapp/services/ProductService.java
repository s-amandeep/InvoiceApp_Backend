package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.dtos.AddProductDto;
import com.zionique.invoiceapp.dtos.GetBrandDto;
import com.zionique.invoiceapp.dtos.GetProductDto;
import com.zionique.invoiceapp.models.Brand;
import com.zionique.invoiceapp.models.PriceOption;
import com.zionique.invoiceapp.models.UnitOfMeasurement;
import com.zionique.invoiceapp.models.Variant;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Variant addNewProduct(AddProductDto addProductDto);

    List<GetProductDto> getAllProducts();

    void deleteProductById(Long id);

    List<Brand> getAllBrands();

    List<PriceOption> getPricesForBrand(Long brandId);

    List<Variant> getVariantsForPriceAndBrand(Long priceId);

    Variant getVariantById(Long id);
}
