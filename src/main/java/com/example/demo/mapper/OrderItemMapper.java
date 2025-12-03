package com.example.demo.mapper;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.id", target = "productId")
    OrderItemDTO toDTO(OrderItem orderItem);

    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "productId", target = "product.id")
    OrderItem toEntity(OrderItemDTO orderItemDTO);
}

