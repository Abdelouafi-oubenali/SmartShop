package com.example.demo.controller;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.exception.ApiResponse;
import com.example.demo.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order_item")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService ;

    @PostMapping
    public OrderItemDTO createOrderItem(@RequestBody OrderItemDTO orderItemDTO)
    {
        return orderItemService.create(orderItemDTO);
    }

    @GetMapping
    public List<OrderItemDTO> getAllOrderItems()
    {
        return orderItemService.getAll() ;
    }

    @GetMapping("/{id}")
    public OrderItemDTO getOrderById(@PathVariable Long id )
    {
        return  orderItemService.getById(id) ;
    }

    @PutMapping("/{id}")
    public OrderItemDTO updateOrderItem(@PathVariable Long id , @RequestBody OrderItemDTO orderItemDTO)
    {
        return  orderItemService.update(id , orderItemDTO) ;
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteOrderItem(@PathVariable Long id)
    {
        return orderItemService.delete(id) ;
    }

}
