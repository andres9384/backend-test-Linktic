package com.example.inventory;

import com.example.inventory.client.ProductClient;
import com.example.inventory.dto.ProductResponseDto;
import com.example.inventory.entity.InventoryEntity;
import com.example.inventory.exception.InsufficientStockException;
import com.example.inventory.exception.InventoryNotFoundException;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.service.impl.InventoryServiceImpl;
//import com.example.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceImplTest {
    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Test
    void shouldPurchaseSuccessfully() {

        Long productId = 2L;
        int cantidad = 2;

        ProductResponseDto product = buildProduct("Mouse", 120.0);

        InventoryEntity inventory = InventoryEntity.builder()
                .productId(productId)
                .cantidad(30)
                .build();

        when(productClient.getProductById(productId)).thenReturn(product);
        when(inventoryRepository.findByProductId(productId))
                .thenReturn(Optional.of(inventory));

        inventoryService.purchase(productId, cantidad);

        assertEquals(28, inventory.getCantidad());
        verify(inventoryRepository).save(inventory);
    }

    private ProductResponseDto buildProduct(String nombre, Double precio) {

        ProductResponseDto.Attributes attributes =
                new ProductResponseDto.Attributes();
        attributes.setNombre(nombre);
        attributes.setPrecio(precio);

        ProductResponseDto.Data data = new ProductResponseDto.Data();
        data.setId(1L);
        data.setAttributes(attributes);

        ProductResponseDto product = new ProductResponseDto();
        product.setData(data);

        return product;
    }



//    @Test
//    void shouldThrowExceptionWhenProductNotFound() {
//
//        Long productId = 99L;
//
//        when(productClient.getProductById(productId))
//                .thenThrow(new ProductNotFoundException(productId));
//
//        assertThrows(ProductNotFoundException.class, () ->
//                inventoryService.purchase(productId, 1)
//        );
//    }

    @Test
    void WhenInventoryNotFound() {

        Long productId = 1L;

        ProductResponseDto product = buildProduct("Teclado", 120.0);

        when(productClient.getProductById(productId)).thenReturn(product);
        when(inventoryRepository.findByProductId(productId))
                .thenReturn(Optional.empty());

        assertThrows(InventoryNotFoundException.class, () ->
                inventoryService.purchase(productId, 1)
        );

        verify(inventoryRepository, never()).save(any());
    }

    @Test
    void WhenStockIsInsufficient() {

        Long productId = 1L;

        ProductResponseDto product = buildProduct("Mouse", 100.0);

        InventoryEntity inventory = InventoryEntity.builder()
                .productId(productId)
                .cantidad(1)
                .build();

        when(productClient.getProductById(productId)).thenReturn(product);
        when(inventoryRepository.findByProductId(productId))
                .thenReturn(Optional.of(inventory));

        assertThrows(InsufficientStockException.class, () ->
                inventoryService.purchase(productId, 2)
        );
    }
}
