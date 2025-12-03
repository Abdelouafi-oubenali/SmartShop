package com.example.demo.mapper;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toDTO(Client client);
    Client toEntity(ClientDTO dto);
}
