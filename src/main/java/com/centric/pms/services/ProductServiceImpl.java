package com.centric.pms.services;

import com.centric.pms.models.Product;
import com.centric.pms.models.Tag;
import com.centric.pms.models.dto.ProductDTO;
import com.centric.pms.repositories.ProductRepository;
import com.centric.pms.utils.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = ProductMapper.INSTANCE.productDtoToProduct(productDTO);

        // copy list of tags from DTO request to put it into model object
        product.setTagSet(productDTO.getTags().stream().map(t -> new Tag(t)).collect(Collectors.toSet()));

        // put UTC time to Model object
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime createdAtZoneTime = now.atZone(ZoneId.systemDefault());
        ZonedDateTime createdAtUTC = createdAtZoneTime.withZoneSameInstant(ZoneId.of("UTC"));
        product.setCreatedAt(createdAtUTC);

        Product productDBReturn = productRepository.save(product);
        ProductDTO productDTOResponse = ProductMapper.INSTANCE.productToProductDTO(productDBReturn);

        // set tag list coming from db to response DTO
        productDTOResponse.setTags(productDBReturn.getTagSet().stream().map(t -> t.getName()).collect(Collectors.toSet()));

        return productDTOResponse;
    }

    @Override
    public List<ProductDTO> findProductByCategory(String catName, int page, int size) {
        Page<Product> products = productRepository.findByCategory(catName, PageRequest.of(page, size, Sort.by("createdAt").descending()));

        List<ProductDTO> productDTOList = products.getContent().stream().
                map(product -> {
                            ProductDTO productDto = ProductMapper.INSTANCE.productToProductDTO(product);
                            productDto.setTags(product.getTagSet().stream().map(t -> t.getName()).collect(Collectors.toSet()));
                            return productDto;
                        }
                ).collect(Collectors.toList());
        return productDTOList;
    }
}
