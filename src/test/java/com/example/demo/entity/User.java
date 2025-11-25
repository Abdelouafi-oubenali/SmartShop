package com.example.demo.entity;

import com.example.demo.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id  ;
    protected String username ;
    protected String password ;
    protected Role role ;

}
