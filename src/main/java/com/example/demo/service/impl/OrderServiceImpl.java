package com.example.demo.service.impl;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.entity.*;
import com.example.demo.exception.ApiResponse;
import com.example.demo.mapper.OrderItemMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PromoCodeRepository;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductRepository productRepository ;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        Order order = new Order();

        if (orderDTO.getClientId() != null) {
            Client client = clientRepository.findById(orderDTO.getClientId())
                    .orElseThrow(() -> new IllegalArgumentException("Client not found"));
            order.setClient(client);
        }

        if (orderDTO.getPromoCodeId() != null) {
            PromoCode promoCode = promoCodeRepository.findById(orderDTO.getPromoCodeId())
                    .orElseThrow(() -> new IllegalArgumentException("Promo code not found"));
            order.setPromoCode(promoCode);
        }

        order.setOrderDate(orderDTO.getOrderDate());
        order.setDiscount(orderDTO.getDiscount());
        order.setTax(orderDTO.getTax());
        order.setTotal(orderDTO.getTotal());
        order.setStatus(orderDTO.getStatus());
        order.setRemainingAmount(orderDTO.getRemainingAmount());

        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            List<OrderItem> orderItems = orderDTO.getItems().stream()
                    .map(dto -> {
                        OrderItem item = new OrderItem();
                        item.setProduitQuantite(dto.getProduitQuantite());
                        item.setTotalLigne(dto.getTotalLigne());

                        if (dto.getProductId() != null) {
                            Product product = productRepository.findById(dto.getProductId())
                                    .orElseThrow(() -> new IllegalArgumentException("Product not found dons ce dto"));
                            item.setProduct(product);
                        }

                        item.setOrder(order);
                        return item;
                    })
                    .collect(Collectors.toList());

            order.setItems(orderItems);
        }

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    @Transactional
    public OrderDTO update(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (orderDTO.getClientId() != null) {
            Client client = clientRepository.findById(orderDTO.getClientId())
                    .orElseThrow(() -> new IllegalArgumentException("Client not found"));
            order.setClient(client);
        }

        if (orderDTO.getPromoCodeId() != null) {
            PromoCode promoCode = promoCodeRepository.findById(orderDTO.getPromoCodeId())
                    .orElseThrow(() -> new IllegalArgumentException("Promo code not found"));
            order.setPromoCode(promoCode);
        }

        order.setOrderDate(orderDTO.getOrderDate());
        order.setDiscount(orderDTO.getDiscount());
        order.setTax(orderDTO.getTax());
        order.setTotal(orderDTO.getTotal());
        order.setStatus(orderDTO.getStatus());
        order.setRemainingAmount(orderDTO.getRemainingAmount());

        if (orderDTO.getItems() != null) {
            if (order.getItems() != null) {
                order.getItems().clear();
            }

            List<OrderItem> orderItems = orderDTO.getItems().stream()
                    .map(dto -> {
                        OrderItem item = new OrderItem();
                        item.setProduitQuantite(dto.getProduitQuantite());
                        item.setTotalLigne(dto.getTotalLigne());

                        if (dto.getProductId() != null) {
//                             Product product = productRepository.findById(dto.getProductId())...
//                             item.setProduct(product);
                        }

                        item.setOrder(order);
                        return item;
                    })
                    .collect(Collectors.toList());

            order.setItems(orderItems);
        }

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return orderMapper.toDTO(order);
    }

    @Override
    @Transactional
    public ApiResponse delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        orderRepository.delete(order);
        return new ApiResponse(true, "Order deleted successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
}