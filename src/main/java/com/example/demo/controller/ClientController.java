package com.example.demo.controller;


import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ClientDTO createClient(@RequestBody Map<String, Object> request) {
        Map<String, Object> clientData = (Map<String, Object>) request.get("client");
        Map<String, Object> userData = (Map<String, Object>) request.get("user");

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName((String) clientData.get("name"));
        clientDTO.setEmail((String) clientData.get("email"));
        clientDTO.setLoyaltyLevel(Enum.valueOf(
                com.example.demo.enums.LoyaltyLevel.class,
                (String) clientData.get("loyaltyLevel"))
        );
        clientDTO.setTotalOrders((Integer) clientData.get("totalOrders"));
        clientDTO.setTotalSpent((Double) clientData.get("totalSpent"));

        UserDto userDto = new UserDto();
        userDto.setUsername((String) userData.get("username"));
        userDto.setPassword((String) userData.get("password"));
        userDto.setRole(Enum.valueOf(
                com.example.demo.enums.Role.class,
                (String) userData.get("role"))
        );

        return clientService.create(clientDTO, userDto);
    }

    @GetMapping
    public List<ClientDTO> getAll()
    {
        return clientService.getAll() ;
    }

    @PostMapping("/update/{id}")
    public ClientDTO updateClient(@PathVariable Long id, @RequestBody Map<String, Object> request)
    {
        Map<String, Object> clientData = (Map<String, Object>) request.get("client");
        Map<String, Object> userData = (Map<String, Object>) request.get("user");

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName((String) clientData.get("name"));
        clientDTO.setEmail((String) clientData.get("email"));
        clientDTO.setLoyaltyLevel(Enum.valueOf(
                com.example.demo.enums.LoyaltyLevel.class,
                (String) clientData.get("loyaltyLevel"))
        );

        clientDTO.setTotalOrders((Integer) clientData.get("totalOrders"));
        clientDTO.setTotalSpent((Double) clientData.get("totalSpent"));

        UserDto userDto = new UserDto();
        userDto.setUsername((String) userData.get("username"));
        userDto.setPassword((String) userData.get("password"));
        userDto.setRole(Enum.valueOf(
                com.example.demo.enums.Role.class,
                (String) userData.get("role"))
        );

        return  clientService.update(id , clientDTO , userDto) ;

    }





}
