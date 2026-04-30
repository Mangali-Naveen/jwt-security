package com.example.jwt_security.controller;


import com.example.jwt_security.dto.ProductRequest;
import com.example.jwt_security.dto.ProductResponse;
import com.example.jwt_security.service.ProductService;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@Valid  @RequestBody ProductRequest request) {
        ProductResponse response = productService.addProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.updateProduct(id,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteProduct(Long id) {

        String message = productService.deleteProduct(id);
        return ResponseEntity.ok(Map.of("message", message));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return  ResponseEntity.ok(productService.getAllProducts());
    }

}
