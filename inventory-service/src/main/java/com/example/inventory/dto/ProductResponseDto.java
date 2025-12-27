package com.example.inventory.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponseDto {
    private Data data;

    @Getter
    @Setter
    public static class Data {
        private Long id;
        private Attributes attributes;
    }

    @Getter
    @Setter
    public static class Attributes {
        private String nombre;
        private Double precio;
        private String descripcion;
    }

}
