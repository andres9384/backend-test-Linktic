package com.example.inventory.integration;

import com.example.inventory.client.ProductClient;
import com.example.inventory.dto.ProductResponseDto;
import com.example.inventory.entity.InventoryEntity;
import com.example.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class InventoryPurchaseIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InventoryRepository inventoryRepository;
    @MockitoBean
    private ProductClient productClient;

    @BeforeEach
    void setUp() {
        inventoryRepository.deleteAll();
        InventoryEntity newInventory = InventoryEntity.builder().productId(1L).cantidad(10).build();
        inventoryRepository.saveAndFlush(newInventory);
    }

    private ProductResponseDto buildProduct(String nombre, Double precio) {
        ProductResponseDto.Attributes attributes = new ProductResponseDto.Attributes();
        attributes.setNombre(nombre);
        attributes.setPrecio(precio);
        ProductResponseDto.Data data = new ProductResponseDto.Data();
        data.setId(1L);
        data.setAttributes(attributes);
        ProductResponseDto product = new ProductResponseDto();
        product.setData(data);
        return product;
    }

    @Test
    void shouldPurchaseSuccessfully() throws Exception {

        ProductResponseDto product = buildProduct("Mouse", 120.0);
        when(productClient.getProductById(1L)).thenReturn(product);

        mockMvc.perform(post("/inventory/purchase")
                .with(user("test").roles("USER"))
                .with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "productId": 1,
                          "cantidad": 2 }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Mouse"))
                .andExpect(jsonPath("$.remainingStock").value(8))
                .andExpect(jsonPath("$.total").value(240.0));
    }

    @Test
    void shouldGetInventoryByProductId() throws Exception {

        mockMvc.perform(get("/inventory/1")
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.attributes.productId").value(1))
                .andExpect(jsonPath("$.data.attributes.cantidad").value(10));
    }

    @Test
    void shouldCreateInventory() throws Exception {

        mockMvc.perform(post("/inventory")
                        .with(user("test").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
              "data": {
                "attributes": {
                  "productId": 2,
                  "cantidad": 15
                }
              }
            }
            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.attributes.productId").value(2))
                .andExpect(jsonPath("$.data.attributes.cantidad").value(15));
    }

    @Test
    void shouldReturn404WhenInventoryNotFound() throws Exception {

        mockMvc.perform(get("/inventory/999")
                        .with(user("test").roles("USER")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].title")
                        .value("Inventory not found"));
    }


}
