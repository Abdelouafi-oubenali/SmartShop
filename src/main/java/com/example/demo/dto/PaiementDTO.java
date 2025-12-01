package com.example.demo.dto;

import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaiementDTO {

    private Long id;

    @NotNull(message = "Le numéro de paiement est obligatoire")
    private Integer paymentNumber;

    @NotNull(message = "Le montant du paiement est obligatoire")
    @PositiveOrZero(message = "Le montant doit être positif ou nul")
    private Double amount;

    @NotNull(message = "Le type de paiement est obligatoire")
    private PaymentType paymentType;

    @NotNull(message = "La date de paiement est obligatoire")
    private LocalDateTime paymentDate;

    private LocalDateTime depositDate;

    @NotNull(message = "Le statut du paiement est obligatoire")
    private PaymentStatus status;

   // @NotNull(message = "L'ID de la commande est obligatoire")
    private Long orderId;

    @NotBlank(message = "La référence du paiement est obligatoire")
    private String reference;

    private String bank; // Optionnel, donc pas de validation
}
