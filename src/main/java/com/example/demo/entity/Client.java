package com.example.demo.entity;

import com.example.demo.enums.LoyaltyLevel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;
    private String email ;

    @Enumerated(EnumType.STRING)
    private LoyaltyLevel loyaltyLevel;

    private Integer totalOrders ;
    private Double totalSpent ;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user ;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders;

}

