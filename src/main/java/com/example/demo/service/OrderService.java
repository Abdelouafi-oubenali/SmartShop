package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.exception.ApiResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public interface OrderService {

    OrderDTO create(OrderDTO orderDTO);
    OrderDTO update(Long id, OrderDTO orderDTO);
    ApiResponse delete(Long id);
    OrderDTO getById(Long id);
    List<OrderDTO> getAll();
    Map<String , Object> getConfirmedOrdersStatsForLoggedUser(Long id) ;
}
