package com.example.inventory.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDto {
    private List<ErrorDetailDto> errors;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ErrorDetailDto {
        private String status;
        private String title;
        private String detail;
    }
}
