package com.example.demo.dto;

import com.example.demo.enums.LoyaltyLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ClientDTO {

    private Long id;

    @NotBlank(message = "Le nom du client est obligatoire")
    private String name;

    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotNull(message = "Le niveau de fidélité est obligatoire")
    private LoyaltyLevel loyaltyLevel;

    @PositiveOrZero(message = "Le nombre total de commandes doit être positif ou nul")
    private Integer totalOrders;

    @PositiveOrZero(message = "Le total dépensé doit être positif ou nul")
    private Double totalSpent;

    private Long userId;
}
