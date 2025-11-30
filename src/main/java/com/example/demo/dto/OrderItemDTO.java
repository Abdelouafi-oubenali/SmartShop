package com.example.demo.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
     private Long id ;
     private  Integer produitQuantite ;
     private Double totalLigne ;
    private Long orderId;
    private Long productId;
}
