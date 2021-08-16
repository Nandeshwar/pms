package com.centric.pms.utils;

import com.centric.pms.models.Product;
import com.centric.pms.models.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product productDtoToProduct(ProductDTO productDTO);
    ProductDTO productToProductDTO(Product product);
}
