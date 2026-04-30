package com.example.jwt_security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String category;

    private String imageUrl;

    public Product() {
    }

    public Product(String category, String description, Long id, String imageUrl, String name, BigDecimal price, Integer stock) {
        this.category = category;
        this.description = description;
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Product(String category, String description, String imageUrl, String name, Integer stock, BigDecimal price) {
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public Product(@NotBlank(message = "Product name is required") String name, @NotBlank(message = "Descripition is required") String descripition, @NotBlank(message = "Price is required") @DecimalMin(value = "0.1", message = "Price must be greater than 0") BigDecimal price, @NotBlank(message = "stock is required") @Min(value = 0, message = "Stock cannot be negative") Integer stock, @NotBlank(message = "Category is required") String category, String s) {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
