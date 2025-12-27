package com.example.inventory.service;

import com.example.inventory.dto.InventoryAttributesDto;
import com.example.inventory.dto.InventoryResponseDto;
import com.example.inventory.dto.PurchaseResponseDto;

public interface InventoryService {
    InventoryResponseDto getInventoryByProductId(Long productId);

    InventoryResponseDto createOrUpdateInventory(InventoryAttributesDto attributes);

    PurchaseResponseDto purchase(Long productId, Integer cantidad);
}
