package com.example.demo.service.impl;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.exception.ApiResponse;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository ;
    private final ProductMapper productMapper ;

    @Override
    public ProductDTO create(ProductDTO productDTO)
    {
        Product product = productMapper.toEntity(productDTO) ;
        productRepository.save(product) ;

        return productMapper.toDTO(product) ;
    }

    @Override
    public List<ProductDTO> getAll()
    {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO update(Long id , ProductDTO productDTO)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product avec id " + id + " not fond")) ;

        product.setName(productDTO.getName());
        product.setUnitPrice(productDTO.getUnitPrice());
        product.setAvailableStock(productDTO.getAvailableStock());
        productRepository.save(product) ;

        return productMapper.toDTO(product);

    }

    @Override
    public ApiResponse delete(Long id)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product avec id " + id + " not fond")) ;

        productRepository.delete(product);
        return new ApiResponse(true , "Product " + id + "deleted") ;
    }

    @Override
    public ProductDTO getById(Long id)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product avec id " + id + " not fond")) ;

        return  productMapper.toDTO(product) ;
    }
}
