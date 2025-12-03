package com.example.demo.service;


import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.PromoCodeDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.ApiResponse;

import java.util.List;

public interface PromoCodeService {

    PromoCodeDTO create(PromoCodeDTO promoCodeDTO);
    PromoCodeDTO update(Long id, PromoCodeDTO promoCodeDTO);
    ApiResponse delete(Long id);
    PromoCodeDTO getById(Long id);
    List<PromoCodeDTO> getAll();
}
