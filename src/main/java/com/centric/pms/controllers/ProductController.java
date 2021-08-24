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
@RequestMapping("/api/v1/products")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @PostMapping(produces = "application/json")
    ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        ProductDTO response = new ProductDTO();
        try {
            response = productService.createProduct(product);
            logger.info("product created successfully");
        } catch (HttpStatusCodeException he) {
            if (he.getStatusCode() == HttpStatus.BAD_REQUEST) {
                logger.error("error creating product=%s".format(he.getMessage()));
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (DataIntegrityViolationException cve) {
            logger.error("error creating product. constraint violation error=%s".format(cve.getMessage()));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("error creating product=%s".format(e.getMessage()));
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = "application/json")
    ResponseEntity<List<ProductDTO>> searchByCategory(@RequestParam String category, @RequestParam int page, @RequestParam int size) {
        return new ResponseEntity(productService.findProductByCategory(category, page, size), HttpStatus.OK);
    }
}

