package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientRequest {

    @NotNull(message = "Les informations du client sont obligatoires")
    @Valid
    private ClientDTO client;

    @NotNull(message = "Les informations de l'utilisateur sont obligatoires")
    @Valid
    private UserDto user;
}
