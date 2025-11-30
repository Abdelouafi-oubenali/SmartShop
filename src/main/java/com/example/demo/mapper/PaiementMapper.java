package com.example.demo.mapper;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.entity.Paiement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaiementMapper {

    @Mapping(source = "order.id", target = "orderId")
    PaiementDTO toDTO(Paiement paiement);

    @Mapping(target = "order", ignore = true)
    Paiement toEntity(PaiementDTO dto);
}
