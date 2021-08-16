package com.centric.pms.controllers;

import com.centric.pms.models.dto.ProductDTO;
import com.centric.pms.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@RestController
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @PostMapping(value = "/v1/products", produces = "application/json")
    ResponseEntity<ProductDTO> newEmployee(@RequestBody ProductDTO product) {
        ProductDTO response = new ProductDTO();
        try {
            response = productService.createProduct(product);
        } catch (HttpStatusCodeException he) {
            if (he.getStatusCode() == HttpStatus.BAD_REQUEST) {
                logger.error("error creating product=%s".formatted(he.getMessage()));
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (DataIntegrityViolationException cve) {
            logger.error("error creating product. constraint violation error=%s".formatted(cve.getMessage()));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("error creating product=%s".formatted(e.getMessage()));
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/products/search", produces = "application/json")
    ResponseEntity<List<ProductDTO>> newEmployee(@RequestParam String category, @RequestParam int page, @RequestParam int size) {
        return new ResponseEntity(productService.findProductByCategory(category, page, size), HttpStatus.OK);
    }
}

