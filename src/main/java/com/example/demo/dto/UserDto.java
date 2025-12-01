package com.example.demo.dto;

import com.example.demo.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id ;
    private String username ;
    private Role role ;
   // private String Password ;
}
