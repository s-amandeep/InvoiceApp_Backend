package com.zionique.invoiceapp.controllers;

import com.zionique.invoiceapp.dtos.*;
import com.zionique.invoiceapp.models.PriceOption;
import com.zionique.invoiceapp.models.Brand;
import com.zionique.invoiceapp.models.UnitOfMeasurement;
import com.zionique.invoiceapp.models.Variant;
import com.zionique.invoiceapp.repositories.VariantRepository;
import com.zionique.invoiceapp.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/products")
public class ProductController {

    private ProductService productService;

    @PostMapping
    public ResponseEntity<Variant> addNewProduct(@RequestBody AddProductDto addProductDto){
        Variant variant = productService.addNewProduct(addProductDto);
        return new ResponseEntity<>(variant, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GetProductDto>> getAllProducts(){
        ResponseEntity<List<GetProductDto>> response = new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        return response;
    }

    @GetMapping("/brands")
    public ResponseEntity<List<GetBrandDto>> getAllBrands(){
        List<Brand> brands = productService.getAllBrands();
        List<GetBrandDto> brandDtos = new ArrayList<>();
        for (Brand brand: brands) {
            GetBrandDto brandDto = new GetBrandDto();
            brandDto.setId(brand.getId());
            brandDto.setName(brand.getName());
            brandDtos.add(brandDto);
        }
        ResponseEntity<List<GetBrandDto>> response = new ResponseEntity<>(brandDtos, HttpStatus.OK);
        return response;
    }

    @GetMapping("/prices")
    public ResponseEntity<List<GetPriceDto>> getPricesForBrand(@RequestParam("brandId") Long brandId){
        List<PriceOption> priceOptions = productService.getPricesForBrand(brandId);
        List<GetPriceDto> priceDtos = new ArrayList<>();
        for (PriceOption priceOption: priceOptions) {
            GetPriceDto priceDto = new GetPriceDto();
            priceDto.setId(priceOption.getId());
            priceDto.setPrice(priceOption.getPrice());
            priceDtos.add(priceDto);
        }
        ResponseEntity<List<GetPriceDto>> response = new ResponseEntity<>(priceDtos, HttpStatus.OK);
        return response;
    }

    @GetMapping("/variants")
    public ResponseEntity<List<VariantDto>> getVariantsForPriceAndBrand(@RequestParam("priceId") Long priceId){
        List<Variant> variants = productService.getVariantsForPriceAndBrand(priceId);
        List<VariantDto> variantDtos = new ArrayList<>();
        for (Variant variant: variants) {
            VariantDto variantDto = new VariantDto();
            variantDto.setId(variant.getId());
            variantDto.setDescription(variant.getDescription());
            variantDto.setTaxRate(variant.getTaxRate());
            variantDto.setCessRate(variant.getCessRate());
            variantDto.setHsnCode(variant.getHsnCode());
            variantDto.setStock(variant.getStock());
            variantDto.setUnitOfMeasurement(variant.getUnitOfMeasurement().getName());
            variantDtos.add(variantDto);
        }
        ResponseEntity<List<VariantDto>> response = new ResponseEntity<>(variantDtos, HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/{variantId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long variantId) {
        try {
            productService.deleteProductById(variantId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
