package com.example.jwt_security.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name is required")
        String name,

        @NotBlank(message = "Descripition is required")
        String description,

        @NotBlank(message = "Price is required")
        @DecimalMin(value = "0.1", message = "Price must be greater than 0")
        BigDecimal price,

        @NotBlank(message = "stock is required")
        @Min(value = 0, message = "Stock cannot be negative")
        Integer stock,
        @NotBlank(message = "Category is required")
        String category,
        String imageUrl
) {
}
