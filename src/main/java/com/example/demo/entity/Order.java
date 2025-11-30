package com.example.demo.entity;


import com.example.demo.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "orders")
public class    Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id  ;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client ;

    private LocalDateTime orderDate ;
    private Double discount ;
    private Double tax ;
    private Double total ;


    @ManyToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode ;

    @Enumerated(EnumType.STRING)
    private OrderStatus status ;

    private Double remainingAmount ;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items  ;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Paiement> payments ;

}
