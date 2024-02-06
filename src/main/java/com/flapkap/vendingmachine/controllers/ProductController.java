package com.flapkap.vendingmachine.controllers;

import com.flapkap.vendingmachine.dtos.ProductDto;
import com.flapkap.vendingmachine.common.GenericException;
import com.flapkap.vendingmachine.common.GenericResponse;
import com.flapkap.vendingmachine.entities.Product;
import com.flapkap.vendingmachine.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/Product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(path = "/addProduct")
    public GenericResponse addProduct(@RequestBody ProductDto productDto) throws GenericException {
        return productService.addProduct(productDto);
    }

    @PutMapping(path = "/updateProduct")
    public GenericResponse updateProduct(@RequestBody ProductDto productDto) throws GenericException {
        return productService.updateProduct(productDto);
    }

    @DeleteMapping(path = "/deleteProduct/{productId}/{sellerId}")
    public GenericResponse deleteProduct(@PathVariable("productId") Integer productId,@PathVariable("sellerId")Integer sellerId) throws GenericException {
        return productService.deleteProduct(productId,sellerId);
    }

    @GetMapping(path = "/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(path = "/getProductById/{productId}")
    public Product getProductById(@PathVariable("productId") Integer productId) throws GenericException{
        return productService.getProductById(productId);
    }
}


