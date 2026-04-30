package com.example.jwt_security.service;

import com.example.jwt_security.dto.ProductRequest;
import com.example.jwt_security.dto.ProductResponse;
import com.example.jwt_security.entity.Product;
import com.example.jwt_security.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public ProductResponse addProduct(ProductRequest request) {
        Product product = new Product(
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.category(),
                request.imageUrl()
        );

        Product saveproduct = productRepo.save(product);
        return mapToResponse(saveproduct);

    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() ->
                new RuntimeException("Product not found with id:" + id));

        existingProduct.setName(request.name());
        existingProduct.setDescription(request.description());
        existingProduct.setPrice(request.price());
        existingProduct.setStock(request.stock());
        existingProduct.setCategory(request.category());
        existingProduct.setImageUrl(request.imageUrl());

        Product updatedproduct = productRepo.save(existingProduct);
        return mapToResponse(updatedproduct);
    }

    public  String deleteProduct(Long id) {
        Product existingProduct = productRepo.findById(id).
                orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        productRepo.delete(existingProduct);
        return "Product delete successfully";
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        return mapToResponse(product);

    }

    public List<ProductResponse> getAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getImageUrl()

        );
    }

}
