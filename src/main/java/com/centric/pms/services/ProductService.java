package com.centric.pms.services;

import com.centric.pms.models.Product;
import com.centric.pms.models.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO product);
    List<ProductDTO> findProductByCategory(String catName, int page, int size);
}
