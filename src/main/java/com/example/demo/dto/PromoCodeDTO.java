package com.example.demo.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class PromoCodeDTO {

    private Long id;

    @NotBlank(message = "Le code promo est obligatoire")
    private String code;

    private boolean active;

    @Min(value = 0, message = "Le pourcentage de réduction doit être au moins 0")
    @Max(value = 100, message = "Le pourcentage de réduction ne peut pas dépasser 100")
    private double discountPercentage;

    @NotNull(message = "La date d'expiration est obligatoire")
    @Future(message = "La date d'expiration doit être dans le futur")
    private Date expirationDate;
}
