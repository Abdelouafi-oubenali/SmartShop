package com.example.demo.dto;

import com.example.demo.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;
    private Long clientId;
    private LocalDateTime orderDate;
    private Double discount;
    private Double tax;
    private Double total;
    private Long promoCodeId;
    private OrderStatus status;
    private Double remainingAmount;
    private List<OrderItemDTO> items;

    //private List<PaiementDTO> payments;
}