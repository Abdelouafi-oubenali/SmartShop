package com.example.demo.dto;

import com.example.demo.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;

    @NotNull(message = "L'ID du client est obligatoire")
    private Long clientId;

    @NotNull(message = "La date de commande est obligatoire")
    private LocalDateTime orderDate;

    @PositiveOrZero(message = "La remise doit être positive ou nulle")
    private Double discount;

    @PositiveOrZero(message = "La taxe doit être positive ou nulle")
    private Double tax;

    //@NotNull(message = "Le total est obligatoire")
    //@PositiveOrZero(message = "Le total doit être positif ou nul")
    private Double total;

    private Long promoCodeId;

    @NotNull(message = "Le statut de la commande est obligatoire")
    private OrderStatus status;

    @PositiveOrZero(message = "Le montant restant doit être positif ou nul")
    private Double remainingAmount;

    @NotEmpty(message = "La commande doit contenir au moins un article")
    @Valid
    private List<OrderItemDTO> items;

    @Valid
    private List<PaiementDTO> payments;
}
