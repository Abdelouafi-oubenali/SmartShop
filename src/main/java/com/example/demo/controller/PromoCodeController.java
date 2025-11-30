package com.example.demo.controller;

import com.example.demo.dto.PromoCodeDTO;
import com.example.demo.exception.ApiResponse;
import com.example.demo.service.ProductService;
import com.example.demo.service.PromoCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promoCode")
@RequiredArgsConstructor
public class PromoCodeController {
    private final PromoCodeService promoCodeService ;

    @GetMapping
    public List<PromoCodeDTO> getAllPromos()
    {
        return promoCodeService.getAll() ;
    }

    @PostMapping
    public PromoCodeDTO CreatePromoCode(@RequestBody PromoCodeDTO promoCodeDTO)
    {
        return promoCodeService.create(promoCodeDTO) ;
    }

    @GetMapping("/{id}")
    public PromoCodeDTO getPromoCodeById(@PathVariable Long id)
    {
        return promoCodeService.getById(id) ;
    }

    @PutMapping("/{id}")
    public PromoCodeDTO updatePromoCode(@PathVariable Long id , @RequestBody PromoCodeDTO promoCodeDTO)
    {
        return promoCodeService.update(id , promoCodeDTO) ;
    }

    @DeleteMapping("/{id}")
    public ApiResponse deletePromoCode(@PathVariable Long id)
    {
        return  promoCodeService.delete(id) ;
    }
}
