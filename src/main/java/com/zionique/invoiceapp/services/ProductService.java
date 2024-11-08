package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.dtos.AddProductDto;
import com.zionique.invoiceapp.dtos.GetProductDto;
import com.zionique.invoiceapp.models.*;

import java.util.List;

public interface ProductService {

    Variant addNewProduct(AddProductDto addProductDto);

    List<GetProductDto> getAllProducts();

    void deleteProductById(Long id);

    List<Brand> getAllBrands();

    List<PriceOption> getPricesForBrand(Long brandId);

    List<Variant> getVariantsForPriceAndBrand(Long priceId);

    Variant getVariantById(Long id);

    void updateVariantByIdAndStock(Long id, Double stock);
}
