package com.example.inventory.exception;


import com.example.inventory.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleInventoryNotFound(
            InventoryNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError("404", "Inventory not found", ex.getMessage()));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponseDto> handleInsufficientStock(
            InsufficientStockException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError("400", "Insufficient stock", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(RuntimeException ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError("500", "Internal server error", ex.getMessage()));
    }

    private ErrorResponseDto buildError(
            String status, String title, String detail) {

        return ErrorResponseDto.builder()
                .errors(List.of(
                        ErrorResponseDto.ErrorDetailDto.builder()
                                .status(status)
                                .title(title)
                                .detail(detail)
                                .build()
                ))
                .build();
    }

}
