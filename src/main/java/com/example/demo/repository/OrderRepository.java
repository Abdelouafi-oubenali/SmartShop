package com.example.demo.repository;

import com.example.demo.entity.Order;
import com.example.demo.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order , Long> {

    Long countByClientIdAndStatus(Long clientId, OrderStatus status);

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.client.id = :clientId AND o.status = :status")
    Double sumTotalByClientAndStatus(@Param("clientId") Long clientId, @Param("status") OrderStatus status);

}

