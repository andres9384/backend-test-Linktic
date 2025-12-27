package com.example.inventory.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PurchaseResponseDto {

    private Long productId;
    private String productName;
    private Double unitPrice;
    private Integer quantity;
    private Double total;
    private Integer remainingStock;
}
