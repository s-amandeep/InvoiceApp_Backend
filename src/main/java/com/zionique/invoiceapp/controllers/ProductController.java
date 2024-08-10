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
    public ResponseEntity<Variant> addNewProduct(@RequestBody AddProductDto addProductDto){
        Variant variant = productService.addNewProduct(addProductDto);
        return new ResponseEntity<>(variant, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GetProductDto>> getAllProducts(){
        ResponseEntity<List<GetProductDto>> response = new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
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
