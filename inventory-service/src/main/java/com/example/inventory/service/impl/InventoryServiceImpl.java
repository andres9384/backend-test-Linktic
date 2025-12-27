package com.example.inventory.service.impl;

import com.example.inventory.client.ProductClient;
import com.example.inventory.dto.InventoryAttributesDto;
import com.example.inventory.dto.InventoryDataDto;
import com.example.inventory.dto.InventoryResponseDto;
import com.example.inventory.dto.PurchaseResponseDto;
import com.example.inventory.entity.InventoryEntity;
import com.example.inventory.exception.InsufficientStockException;
import com.example.inventory.exception.InventoryNotFoundException;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final ProductClient productClient;

    @Override
    public InventoryResponseDto getInventoryByProductId(Long productId) {
        InventoryEntity inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(productId));

        return mapToResponse(inventory);
    }

    @Override
    public InventoryResponseDto createOrUpdateInventory(InventoryAttributesDto attributes) {
        InventoryEntity inventory = inventoryRepository
                .findByProductId(attributes.getProductId())
                .orElse(
                        InventoryEntity.builder()
                                .productId(attributes.getProductId())
                                .cantidad(0)
                                .build()
                );

        inventory.setCantidad(attributes.getCantidad());

        InventoryEntity saved = inventoryRepository.save(inventory);

        return mapToResponse(saved);
    }

    @Transactional
    @Override
    public PurchaseResponseDto purchase(Long productId, Integer cantidad) {

        var product = productClient.getProductById(productId);

        System.out.println(product);

        InventoryEntity inventory = inventoryRepository
                .findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(productId));

        if (inventory.getCantidad() < cantidad) {
            throw new InsufficientStockException(productId);
        }

        inventory.setCantidad(inventory.getCantidad() - cantidad);

        inventoryRepository.save(inventory);

        double unitPrice = product.getData().getAttributes().getPrecio();
        double total = unitPrice * cantidad;


        return PurchaseResponseDto.builder()
                .productId(productId)
                .productName(product.getData().getAttributes().getNombre())
                .unitPrice(unitPrice)
                .quantity(cantidad)
                .total(total)
                .remainingStock(inventory.getCantidad())
                .build();
    }

    private InventoryResponseDto mapToResponse(InventoryEntity inventory) {

        return InventoryResponseDto.builder()
                .data(
                        InventoryDataDto.builder()
                                .type("inventory")
                                .id(String.valueOf(inventory.getId()))
                                .attributes(
                                        InventoryAttributesDto.builder()
                                                .productId(inventory.getProductId())
                                                .cantidad(inventory.getCantidad())
                                                .build()
                                )
                                .build()
                )
                .build();
    }


}
