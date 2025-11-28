package com.example.demo.service;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.exception.ApiResponse;

import java.util.List;

public interface OrderItemService {

    OrderItemDTO create(OrderItemDTO orderItemDTO);
    OrderItemDTO update(Long id, OrderItemDTO orderItemDTO);
    ApiResponse delete(Long id);
    OrderItemDTO getById(Long id);
    List<OrderItemDTO> getAll();


}

