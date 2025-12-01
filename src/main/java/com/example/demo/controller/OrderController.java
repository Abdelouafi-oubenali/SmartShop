package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.enums.Role;
import com.example.demo.exception.ApiResponse;
import com.example.demo.service.HelperService;
import com.example.demo.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final HelperService helperService ;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO , HttpServletRequest httpRequest) {

        if(!helperService.isAuthenticated(httpRequest)) {
            return ResponseEntity.status(401).body("tu ne pas login");
        }

        if (!helperService.hasRole(httpRequest, Role.ADMIN)) {
            return ResponseEntity.status(403).body("vous n'avez pas la permission pour faire cette action : ADMIN only");
        }

        OrderDTO createdOrder = orderService.create(orderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO , HttpServletRequest httpRequest) {

        if(!helperService.isAuthenticated(httpRequest)) {
            return ResponseEntity.status(401).body("tu ne pas login");
        }

        if (!helperService.hasRole(httpRequest, Role.ADMIN)) {
            return ResponseEntity.status(403).body("vous n'avez pas la permission pour faire cette action : ADMIN only");
        }


        OrderDTO updatedOrder = orderService.update(id, orderDTO);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id , HttpServletRequest httpRequest) {

        if(!helperService.isAuthenticated(httpRequest)) {
            return ResponseEntity.status(401).body("tu ne pas login");
        }

        if (!helperService.hasRole(httpRequest, Role.ADMIN)) {
            return ResponseEntity.status(403).body("vous n'avez pas la permission pour faire cette action : ADMIN only");
        }

        OrderDTO order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id , HttpServletRequest httpRequest) {

        if(!helperService.isAuthenticated(httpRequest)) {
            return ResponseEntity.status(401).body("tu ne pas login");
        }

        if (!helperService.hasRole(httpRequest, Role.ADMIN)) {
            return ResponseEntity.status(403).body("vous n'avez pas la permission pour faire cette action : ADMIN only");
        }

        ApiResponse response = orderService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(HttpServletRequest httpRequest) {

        if(!helperService.isAuthenticated(httpRequest)) {
            return ResponseEntity.status(401).body("tu ne pas login");
        }

        if (!helperService.hasRole(httpRequest, Role.ADMIN)) {
            return ResponseEntity.status(403).body("vous n'avez pas la permission pour faire cette action : ADMIN only");
        }

        List<OrderDTO> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }
}