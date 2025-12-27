package com.example.product.service;

import com.example.product.dto.ProductAttributesDto;
import com.example.product.dto.ProductResponseDto;

public interface ProductService {
    ProductResponseDto createProduct(ProductAttributesDto attributes);

    ProductResponseDto getProductById(Long id);
}
