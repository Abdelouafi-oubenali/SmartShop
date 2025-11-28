package com.example.demo.service.impl;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.exception.ApiResponse;
import com.example.demo.mapper.OrderItemMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrderItemService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class OrderItemServiceIml implements OrderItemService {

    private  final  OrderItemMapper orderItemMapper ;
    private  final OrderItemRepository orderItemRepository ;
    private final ProductMapper productMapper ;
    private final OrderRepository orderRepository ;
    private final ProductRepository productRepository ;

    public OrderItemDTO create(OrderItemDTO orderItemDTO) {

        OrderItem orderItem = new OrderItem();

        Order order = orderRepository.findById(orderItemDTO.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Product product = productRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        orderItem.setProduitQuantite(orderItemDTO.getProduitQuantite());
        orderItem.setTotalLigne(orderItemDTO.getTotalLigne());
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        orderItemRepository.save(orderItem);

        return orderItemMapper.toDTO(orderItem);
    }


    @Override
    public OrderItemDTO update(Long id, OrderItemDTO orderItemDTO)
    {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("le order avec ce id " + id + " ne se trouve pas"));

        orderItem.setProduitQuantite(orderItemDTO.getProduitQuantite());
        orderItem.setTotalLigne(orderItemDTO.getTotalLigne());

        Product product = productRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));

        orderItem.setProduct(product);

        orderItem = orderItemRepository.save(orderItem);

        return orderItemMapper.toDTO(orderItem);
    }


    @Override
    public ApiResponse delete(Long id)
     {
         OrderItem orderItem = orderItemRepository.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("le order avec ce id " + id + "no trouve pas")) ;

         orderItemRepository.delete(orderItem);
         return new ApiResponse(false , "le order item update avec sucice ")  ;

     }

     @Override
    public OrderItemDTO getById(Long id)
     {
         OrderItem orderItem = orderItemRepository.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("le order avec ce id " + id + "no trouve pas")) ;

         return orderItemMapper.toDTO(orderItem) ;

     }

    @Override
    public List<OrderItemDTO> getAll()
    {
        List<OrderItem> orderItems = orderItemRepository.findAll();

        return orderItems.stream()
                .map(orderItemMapper::toDTO)
                .toList();
    }

}