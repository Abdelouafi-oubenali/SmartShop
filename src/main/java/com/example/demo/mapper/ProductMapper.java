package com.example.demo.mapper;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product) ;
    Product toEntity(ProductDTO productDTO) ;
}
