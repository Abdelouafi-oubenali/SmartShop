package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class OrderItemDTO {

    private Long id;

    @NotNull(message = "La quantité du produit est obligatoire")
    @Positive(message = "La quantité doit être supérieure à zéro")
    private Integer produitQuantite;

//    @NotNull(message = "Le total de la ligne est obligatoire")
//    @PositiveOrZero(message = "Le total de la ligne doit être positif ou nul")
    private Double totalLigne;

   // @NotNull(message = "L'ID de la commande est obligatoire")
    private Long orderId;

    @NotNull(message = "L'ID du produit est obligatoire")
    private Long productId;
}
