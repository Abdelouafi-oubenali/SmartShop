package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.exception.ApiResponse;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductDTO CreateProduct(@RequestBody ProductDTO request)
    {
        return productService.create(request) ;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts()
    {
        return productService.getAll() ;
    }

    @GetMapping("/{id}")

    public ProductDTO getProductById(@PathVariable Long id)
    {
        return  productService.getById(id) ;
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id , @RequestBody ProductDTO productDTO)
    {
        return productService.update(id, productDTO) ;
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteProduct(@PathVariable Long id)
    {
        return  productService.delete(id);
    }

}
