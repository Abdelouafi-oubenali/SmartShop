package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Le nom du client est obligatoire")
    private String username;

    @NotBlank(message = "Le mot pass du client est obligatoire")
    private String password;
}

