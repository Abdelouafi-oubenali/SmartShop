package com.example.demo.controller;


import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Client;
import com.example.demo.enums.Role;
import com.example.demo.exception.ApiResponse;
import com.example.demo.service.ClientService;
import com.example.demo.service.HelperService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final HelperService helperService ;

    @PostMapping
    public ResponseEntity<?> createClient(
            @RequestBody Map<String, Object> request,
            HttpServletRequest httpRequest) {

        if(!helperService.isAuthenticated(httpRequest)) {
            return ResponseEntity.status(401).body("tu ne pas login");
        }

        if (!helperService.hasRole(httpRequest, Role.ADMIN)) {
            return ResponseEntity.status(403).body("vous n'avez pas la permission pour faire cette action : ADMIN only");
        }

        Map<String, Object> clientData = (Map<String, Object>) request.get("client");
        Map<String, Object> userData = (Map<String, Object>) request.get("user");

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName((String) clientData.get("name"));
        clientDTO.setEmail((String) clientData.get("email"));
        clientDTO.setLoyaltyLevel(Enum.valueOf(
                com.example.demo.enums.LoyaltyLevel.class,
                (String) clientData.get("loyaltyLevel")
        ));
        clientDTO.setTotalOrders((Integer) clientData.get("totalOrders"));
        clientDTO.setTotalSpent((Double) clientData.get("totalSpent"));

        UserDto userDto = new UserDto();
        userDto.setUsername((String) userData.get("username"));
        userDto.setRole(Enum.valueOf(
                com.example.demo.enums.Role.class,
                (String) userData.get("role")
        ));

        return ResponseEntity.ok(clientService.create(clientDTO, userDto));
    }


    @GetMapping
    public List<ClientDTO> getAll()
    {
        return clientService.getAll() ;
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateClient(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request,
            HttpServletRequest httpRequest) {

        if (!helperService.isAuthenticated(httpRequest)) {
            return ResponseEntity.status(401).body("tu ne pas login");
        }

        if (!helperService.hasRole(httpRequest, Role.ADMIN)) {
            return ResponseEntity.status(403).body("vous n'avez pas la permission pour faire cette action : ADMIN only");
        }

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
        //userDto.setPassword((String) userData.get("password"));
        userDto.setRole(Enum.valueOf(
                com.example.demo.enums.Role.class,
                (String) userData.get("role"))
        );

        return ResponseEntity.ok(clientService.update(id, clientDTO, userDto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id , HttpServletRequest httpRequest)
    {
        if (!helperService.isAuthenticated(httpRequest)) {
            return ResponseEntity.status(401).body("tu ne pas login");
        }

        if (!helperService.hasRole(httpRequest, Role.ADMIN)) {
            return ResponseEntity.status(403).body("vous n'avez pas la permission pour faire cette action : ADMIN only");
        }

        ApiResponse response = clientService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id , HttpServletRequest httpRequest)
    {
        if (!helperService.isAuthenticated(httpRequest)) {
            return ResponseEntity.status(401).body("tu ne pas login");
        }

        if (!helperService.hasRole(httpRequest, Role.ADMIN)) {
            return ResponseEntity.status(403).body("vous n'avez pas la permission pour faire cette action : ADMIN only");
        }
        ClientDTO response =  clientService.getById(id) ;
        return   ResponseEntity.ok(response) ;
    }


}
