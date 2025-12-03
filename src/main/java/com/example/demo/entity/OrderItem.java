package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id  ;

    private Integer produitQuantite ;
    private Double totalLigne ;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product ;

}
