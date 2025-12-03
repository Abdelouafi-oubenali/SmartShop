package com.example.demo.dto;

import com.example.demo.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    private Long id ;
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;

    @NotNull(message = "Le rôle est obligatoire")
    private Role role;
    private String password ;
}
