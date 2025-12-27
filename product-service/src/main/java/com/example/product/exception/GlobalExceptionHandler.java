package com.example.product.exception;

import com.example.product.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleProductNotFound(ProductNotFoundException ex) {

        ErrorResponseDto response = ErrorResponseDto.builder()
                .errors(List.of(
                        ErrorResponseDto.ErrorDetailDto.builder()
                                .status("404")
                                .title("Product not found")
                                .detail(ex.getMessage())
                                .build()
                ))
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<ErrorResponseDto.ErrorDetailDto> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        ErrorResponseDto.ErrorDetailDto.builder()
                                .status("400")
                                .title("Validation error")
                                .detail(error.getField() + ": " + error.getDefaultMessage())
                                .build()
                )
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDto.builder().errors(errors).build());
    }
}
