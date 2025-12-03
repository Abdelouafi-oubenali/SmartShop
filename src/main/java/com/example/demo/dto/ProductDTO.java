package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Le nom du produit est obligatoire")
    private String name;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @Positive(message = "Le prix unitaire doit être supérieur à zéro")
    private Double unitPrice;

    @NotNull(message = "Le stock disponible est obligatoire")
    @PositiveOrZero(message = "Le stock disponible doit être positif ou nul")
    private Integer availableStock;
}
