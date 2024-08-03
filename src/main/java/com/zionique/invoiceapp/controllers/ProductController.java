package com.zionique.invoiceapp.controllers;

import com.zionique.invoiceapp.dtos.GetProductDto;
import com.zionique.invoiceapp.dtos.PriceOptionDto;
import com.zionique.invoiceapp.dtos.ProductDto;
import com.zionique.invoiceapp.dtos.VariantDto;
import com.zionique.invoiceapp.models.PriceOption;
import com.zionique.invoiceapp.models.Product;
import com.zionique.invoiceapp.models.Variant;
import com.zionique.invoiceapp.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDto productDto){
        Product product = productService.addNewProduct(getProductFromProductDto(productDto));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GetProductDto>> getAllProducts(){
        ResponseEntity<List<GetProductDto>> response = new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
        Optional<Product> optionalProduct = productService.getProductById(id);
        ResponseEntity<Product> response = new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
        return response;
    }

//    @GetMapping("/company/{company}")
//    public ResponseEntity<List<Product>> getProductsByCompany(@PathVariable("company") String companyName){
//        Optional<List<Product>> optionalProduct = productService.getProductsByCompany(companyName);
//        ResponseEntity<List<Product>> response = new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
//        return response;
//    }

//    @GetMapping("/company/{company}/mrp/{mrp}")
//    public ResponseEntity<List<Product>> getProductsByCompanyAndMrp(@PathVariable("company") String companyName,
//                                                                    @PathVariable("mrp") Float mrp){
//        Optional<List<Product>> optionalProduct = productService.getProductsByCompanyAndMrp(companyName, mrp);
//        ResponseEntity<List<Product>> response = new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
//        return response;
//    }

    private Product getProductFromProductDto(ProductDto productDto) {
        Product product = new Product();
        product.setBrandName(productDto.getBrandName());

        Set<PriceOption> priceOptions = productDto.getPriceOptions().stream()
                .map(this::getPriceOptionFromPriceOptionDto)
                .collect(Collectors.toSet());

        priceOptions.forEach(product::addPriceOption);

        return product;
    }

    private PriceOption getPriceOptionFromPriceOptionDto(PriceOptionDto priceOptionDTO) {
        PriceOption priceOption = new PriceOption();
        priceOption.setPrice(priceOptionDTO.getPrice());

        Set<Variant> variants = priceOptionDTO.getVariants().stream()
                .map(this::getVariantFromVariantDto)
                .collect(Collectors.toSet());

        variants.forEach(priceOption::addVariant);
        return priceOption;
    }

    private Variant getVariantFromVariantDto(VariantDto variantDTO) {
        Variant variant = new Variant();
        variant.setDescription(variantDTO.getDescription());
        return variant;
    }
}
