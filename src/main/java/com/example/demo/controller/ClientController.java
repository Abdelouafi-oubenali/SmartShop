package com.example.demo.controller;


import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ClientRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Client;
import com.example.demo.enums.Role;
import com.example.demo.exception.ApiResponse;
import com.example.demo.service.ClientService;
import com.example.demo.service.HelperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
            @Valid
            @RequestBody ClientRequest request,
            HttpServletRequest httpRequest) {

        if (!helperService.isAuthenticated(httpRequest)) {
            return ResponseEntity.status(401).body("Tu n'es pas connecté");
        }

        if (!helperService.hasRole(httpRequest, Role.ADMIN)) {
            return ResponseEntity.status(403).body("Action réservée aux ADMIN");
        }


        ClientDTO clientDTO = request.getClient();
        UserDto userDto = request.getUser();

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
            @Valid @RequestBody ClientRequest request,
            HttpServletRequest httpRequest) {

        if (!helperService.isAuthenticated(httpRequest)) {
            return ResponseEntity.status(401).body("Tu n'es pas connecté");
        }

//    if (!helperService.hasRole(httpRequest, Role.ADMIN)) {
//        return ResponseEntity.status(403).body("Action réservée aux ADMIN");
//    }

        ClientDTO clientDTO = request.getClient();
        UserDto userDto = request.getUser();

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
