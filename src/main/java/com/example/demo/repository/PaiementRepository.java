package com.example.demo.repository;

import com.example.demo.dto.PaiementDTO;
import com.example.demo.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Optional<Paiement> findByOrderId(Long orderId);}
