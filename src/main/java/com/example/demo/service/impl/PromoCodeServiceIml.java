package com.example.demo.service.impl;

import com.example.demo.dto.PromoCodeDTO;
import com.example.demo.entity.PromoCode;
import com.example.demo.exception.ApiResponse;
import com.example.demo.mapper.PromoCodeMapper;
import com.example.demo.repository.PromoCodeRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.PromoCodeService;
import lombok.Data;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class PromoCodeServiceIml implements PromoCodeService {

    private final PromoCodeRepository promoCodeRepository ;
    private final PromoCodeMapper promoCodeMapper ;

    @Override
    public PromoCodeDTO create(PromoCodeDTO promoCodeDTO) {
        PromoCode promoCode = promoCodeMapper.toEntity(promoCodeDTO);
        PromoCode savedPromoCode = promoCodeRepository.save(promoCode);
        return promoCodeMapper.toDTO(savedPromoCode);
    }

    @Override
    public PromoCodeDTO update(Long id , PromoCodeDTO promoCodeDTO)
    {
        PromoCode promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("le promo code avec ce id no trouve"));

        promoCode.setCode(promoCodeDTO.getCode());
        promoCode.setActive(promoCodeDTO.isActive());
        promoCode.setDiscountPercentage(promoCodeDTO.getDiscountPercentage());
        promoCode.setExpirationDate(promoCodeDTO.getExpirationDate());

        PromoCode save = promoCodeRepository.save(promoCode) ;

        return promoCodeMapper.toDTO(save) ;
    }

    @Override
    public ApiResponse delete(Long id)
    {
        PromoCode promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("promo code avec ce id ne exist pas  ")) ;
         promoCodeRepository.delete(promoCode);
         return  new ApiResponse(true , "promo code avec id " + id + "supprimed") ;
    }

    @Override
    public PromoCodeDTO getById(Long id)
    {
        PromoCode promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("promo code avec ce id ne exist pas  ")) ;

        return promoCodeMapper.toDTO(promoCode) ;
    }

    @Override
    public List<PromoCodeDTO> getAll()
    {
        return promoCodeRepository.findAll()
                .stream()
                .map(promoCodeMapper::toDTO)
                .toList();
    }


}
