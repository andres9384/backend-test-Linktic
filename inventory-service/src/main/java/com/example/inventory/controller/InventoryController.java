package com.example.inventory.controller;

import com.example.inventory.dto.InventoryRequestDto;
import com.example.inventory.dto.InventoryResponseDto;
import com.example.inventory.dto.PurchaseRequestDto;
import com.example.inventory.dto.PurchaseResponseDto;
import com.example.inventory.service.InventoryService;
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
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    /**
     * Consultar inventario por productId
     */
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDto> getInventoryByProductId(
            @PathVariable Long productId) {

        InventoryResponseDto response =
                inventoryService.getInventoryByProductId(productId);

        return ResponseEntity.ok(response);
    }

    /**
     * Crear o actualizar inventario
     */
    @PostMapping
    public ResponseEntity<InventoryResponseDto> createOrUpdateInventory(
            @Valid @RequestBody InventoryRequestDto request) {

        InventoryResponseDto response =
                inventoryService.createOrUpdateInventory(
                        request.getData().getAttributes()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Crear un apeticion para comprar
     */
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponseDto> purchase(@RequestBody PurchaseRequestDto request) {

        PurchaseResponseDto response = inventoryService.purchase(
                request.getProductId(),
                request.getCantidad()
        );

        return ResponseEntity.ok(response);
    }


}
