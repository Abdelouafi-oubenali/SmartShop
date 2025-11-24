package com.example.demo.entity;

import com.example.demo.enums.LoyaltyLevel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Client extends User {

    @Enumerated(EnumType.STRING)
    private LoyaltyLevel loyaltyLevel;

    private Integer totalOrders ;
    private Double totalSpent ;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders;

}
