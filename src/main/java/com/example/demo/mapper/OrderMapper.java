package com.example.demo.mapper;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "promoCode.id", target = "promoCodeId")
    OrderDTO toDTO(Order order);

    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "promoCodeId", target = "promoCode.id")
    @Mapping(target = "items", ignore = true)
    Order toEntity(OrderDTO dto);
}



