package com.example.product.controller;

import com.example.product.dto.ProductRequestDto;
import com.example.product.dto.ProductResponseDto;
import com.example.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @Valid @RequestBody ProductRequestDto request) {

        ProductResponseDto response =
                productService.createProduct(request.getData().getAttributes());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(
            @PathVariable Long id) {

        ProductResponseDto response = productService.getProductById(id);

        return ResponseEntity.ok(response);
    }
}
