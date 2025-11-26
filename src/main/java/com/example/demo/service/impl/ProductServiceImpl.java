package com.example.demo.service.impl;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository ;
    private final ProductMapper productMapper ;



    public ProductDTO create(ProductDTO productDTO)
    {
        Product product = productMapper.toEntity(productDTO) ;
        productRepository.save(product) ;

        return productMapper.toDTO(product) ;
    }
}
