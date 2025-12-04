package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.PaiementDTO;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.ApiResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public interface OrderService {

    OrderDTO create(OrderDTO orderDTO);
    OrderDTO updateStatus(Long id, OrderStatus orderStatus);
    ApiResponse delete(Long id);
    OrderDTO getById(Long id);
    List<OrderDTO> getAll();
    ApiResponse completePayment(Long orderId , PaiementDTO paiementDTO) ;
    Map<String , Object> getConfirmedOrdersStatsForLoggedUser(Long id) ;
}
