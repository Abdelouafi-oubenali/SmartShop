package com.example.demo.dto;

import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.PaymentType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaiementDTO {

    private Long id;
    private Integer paymentNumber;
    private Double amount;
    private PaymentType paymentType;
    private LocalDateTime paymentDate;
    private LocalDateTime depositDate;
    private PaymentStatus status;
    private Long orderId;
    private String reference;
    private String bank;
}
