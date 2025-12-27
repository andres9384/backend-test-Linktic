package com.example.product.service;

import com.example.product.entity.ProductEntity;
import com.example.product.dto.ProductAttributesDto;
import com.example.product.exception.ProductNotFoundException;
import com.example.product.service.impl.ProductServiceImpl;
import com.example.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldCreateProductSuccessfully() {
        ProductAttributesDto attributes = ProductAttributesDto.builder()
                .nombre("Mouse")
                .precio(50.0)
                .descripcion("Inalámbrico")
                .build();

        ProductEntity savedEntity = ProductEntity.builder()
                .id(1L)
                .nombre("Mouse")
                .precio(50.0)
                .descripcion("Inalámbrico")
                .build();

        when(productRepository.save(any(ProductEntity.class)))
                .thenReturn(savedEntity);

        var response = productService.createProduct(attributes);

        assertNotNull(response);
        assertEquals("products", response.getData().getType());
        assertEquals("1", response.getData().getId());
        assertEquals("Mouse", response.getData().getAttributes().getNombre());

        verify(productRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(99L));
    }
}
