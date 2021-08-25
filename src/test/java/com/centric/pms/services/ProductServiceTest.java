package com.centric.pms.services;

import com.centric.pms.models.Product;
import com.centric.pms.models.Tag;
import com.centric.pms.models.dto.ProductDTO;
import com.centric.pms.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void createProduct() {
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

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(productName);
        productDTO.setDescription(productDescription);
        productDTO.setBrand(productBrand);
        productDTO.setTags(new HashSet<>(Arrays.asList(tag1, tag2, tag3)));
        productDTO.setCategory(category);

        Product product = new Product();
        product.setId(UUID.fromString(uuid));
        product.setName(productName);
        product.setDescription(productDescription);
        product.setBrand(productBrand);
        product.setTagSet(productDTO.getTags().stream().map(t -> new Tag(t)).collect(Collectors.toSet()));
        product.setCategory(category);
        product.setCreatedAt(createdAtUTC);

        when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDTO responseDTO = productService.createProduct(productDTO);

        Assertions.assertEquals(product.getId(), responseDTO.getId());
        Assertions.assertEquals(product.getCreatedAt(), responseDTO.getCreatedAt());

    }
}
