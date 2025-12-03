package com.example.demo.entity;

import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private Integer paymentNumber ;
    private Double amount ;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType ;

    private LocalDateTime paymentDate ;
    private LocalDateTime depositDate ;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order ;

    private String reference ;

    private String bank  ;





}
