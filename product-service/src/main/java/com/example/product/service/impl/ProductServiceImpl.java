package com.example.product.service.impl;

import com.example.product.entity.ProductEntity;
import com.example.product.dto.ProductAttributesDto;
import com.example.product.dto.ProductDataDto;
import com.example.product.dto.ProductResponseDto;
import com.example.product.exception.ProductNotFoundException;
import com.example.product.service.ProductService;
import com.example.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct(ProductAttributesDto attributes) {

        ProductEntity entity = ProductEntity.builder()
                .nombre(attributes.getNombre())
                .precio(attributes.getPrecio())
                .descripcion(attributes.getDescripcion())
                .build();

        ProductEntity saved = productRepository.save(entity);

        return mapToResponse(saved);
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return mapToResponse(product);
    }

    private ProductResponseDto mapToResponse(ProductEntity product) {

        return ProductResponseDto.builder()
                .data(
                        ProductDataDto.builder()
                                .type("products")
                                .id(String.valueOf(product.getId()))
                                .attributes(
                                        ProductAttributesDto.builder()
                                                .nombre(product.getNombre())
                                                .precio(product.getPrecio())
                                                .descripcion(product.getDescripcion())
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
