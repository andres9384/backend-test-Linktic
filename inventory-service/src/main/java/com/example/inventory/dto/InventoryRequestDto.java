package com.example.inventory.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequestDto {
    @NotNull
    private InventoryDataRequestDto data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InventoryDataRequestDto {

        @NotNull
        private String type;

        @Valid
        @NotNull
        private InventoryAttributesDto attributes;
    }
}
