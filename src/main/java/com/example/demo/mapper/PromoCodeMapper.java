package com.example.demo.mapper;

import com.example.demo.dto.PromoCodeDTO;
import com.example.demo.entity.PromoCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PromoCodeMapper {

    PromoCodeDTO toDTO(PromoCode promoCode) ;
    PromoCode toEntity(PromoCodeDTO promoCodeDTO) ;

}
