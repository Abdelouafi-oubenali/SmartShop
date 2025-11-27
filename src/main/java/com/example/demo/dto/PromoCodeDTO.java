package com.example.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PromoCodeDTO {
    private Long id  ;
    private String code ;
    private  boolean active ;
    private double discountPercentage ;
    private Date expirationDate ;

}
