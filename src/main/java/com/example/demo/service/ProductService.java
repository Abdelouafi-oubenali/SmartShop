package com.example.demo.service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ProductService {
    ProductDTO create(ProductDTO productDTO);
//    ClientDTO update(Long id, ClientDTO clientDTO , UserDto userDto);
//    ApiResponse delete(Long id);
//    ClientDTO getById(Long id);
//    List<ClientDTO> getAll();
}
