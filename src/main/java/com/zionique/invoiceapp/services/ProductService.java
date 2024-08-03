package com.zionique.invoiceapp.services;

import com.zionique.invoiceapp.dtos.GetProductDto;
import com.zionique.invoiceapp.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product addNewProduct(Product product);

    List<GetProductDto> getAllProducts();

    Optional<Product> getProductById(Long id);

//    Optional<List<Product>> getProductsByCompany(String productCompany);
//
//    Optional<List<Product>> getProductsByCompanyAndMrp(String productCompany, Float mrp);

}
