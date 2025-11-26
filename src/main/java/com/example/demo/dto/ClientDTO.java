package com.example.demo.dto;

import com.example.demo.enums.LoyaltyLevel;
import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String name;
    private String email ;
    private LoyaltyLevel loyaltyLevel;
    private Integer totalOrders;
    private Double totalSpent;

    private Long userId;
}