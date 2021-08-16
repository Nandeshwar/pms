package com.centric.pms.controllers;

import com.centric.pms.models.dto.ProductDTO;
import com.centric.pms.services.ProductService;
import com.centric.pms.services.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

//@ExtendWith(SpringExtension.class)

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("failure: product creation endpoint without body is bad request")
    public void whenNullValue_thenReturns400() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @DisplayName("success: product creation endpoint")
    public void createProduct() throws Exception {
        mockMvc.perform(post("/v1/products")
                .content(asJsonString(new ProductDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("success: search product by category")
    public void searchProductByCategory() throws Exception {
        String uuid = "8746f9af-5333-40cb-8050-3c473bcf475d";
        String productName = "Red Shirt";
        String productDescription = "Red hugo boss shirt";
        String productBrand = "Hugo Boss";
        String tag1 = "red";
        String tag2 = "shirt";
        String tag3 = "slim fit";
        String category = "apparel";

        String dateTimeStr = "2021-08-15T11:30:00Z";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

        ZonedDateTime createdAtZoneTime = dateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime createdAtUTC = createdAtZoneTime.withZoneSameInstant(ZoneId.of("UTC"));

        String expectedJSON = String.format("""
                {
                     "id": "%s",
                     "name": "%s",
                     "description": "%s",
                     "brand": "%s",
                     "tags": [
                         "%s",
                         "%s",
                         "%s"
                     ],
                     "category": "%s",
                     "createdAt": "%s"
                 }
                """, uuid, productName, productDescription, productBrand, tag1, tag2, tag3, category, dateTimeStr);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(UUID.fromString(uuid));
        productDTO.setName(productName);
        productDTO.setDescription(productDescription);
        productDTO.setBrand(productBrand);
        productDTO.setTags(new HashSet<>(Arrays.asList(tag1, tag2, tag3)));
        productDTO.setCategory(category);
        productDTO.setCreatedAt(createdAtUTC);

        ProductDTO productDTOInput = new ProductDTO();
        productDTOInput.setName(productName);
        productDTOInput.setDescription(productDescription);
        productDTOInput.setBrand(productBrand);
        productDTOInput.setTags(new HashSet<>(Arrays.asList(tag1, tag2, tag3)));
        productDTOInput.setCategory(category);

        List<ProductDTO> productList = new ArrayList<>();
        productList.add(productDTO);

        when(productService.findProductByCategory("Apparel", 0, 1)).thenReturn(productList);

        //MvcResult mvcResult = mockMvc.perform(get("/v1/products/search?category=AppareL&page=0&size=10")
        MvcResult mvcResult = mockMvc.perform(get("/v1/products/search")
                .param("category", "AppareL")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
    }

    static String asJsonString(final Object obj) {
        try {
            String s = new ObjectMapper().writeValueAsString(obj);
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
