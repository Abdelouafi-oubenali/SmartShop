package com.example.demo.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id ;
    private String name ;
    private Double unitPrice ;
    private Integer availableStock ;
}
