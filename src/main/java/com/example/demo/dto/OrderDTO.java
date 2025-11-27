package com.example.demo.dto;

import com.example.demo.entity.Client;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.PromoCode;
import com.example.demo.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;



public class OrderDTO {
    private Long id;
    private ClientDTO client;
    private LocalDateTime orderDate;
    private Double discount;
    private Double tax;
    private Double total;
    private PromoCodeDTO promoCode;
    private OrderStatus status;
    private Double remainingAmount;
    //private List<OrderItemDTO> items;
    //private List<PaiementDTO> payments;
}


