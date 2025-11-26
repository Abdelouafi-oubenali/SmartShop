package com.example.demo.service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.UserDto;

import java.util.List;

public interface ClientService {
    ClientDTO create(ClientDTO clientDTO, UserDto userDto);
    ClientDTO update(Long id, ClientDTO clientDTO , UserDto userDto);
     void delete(Long id);
//    ClientDTO getById(Long id);
      List<ClientDTO> getAll();
}