package com.example.demo.repository;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client , Long> {
    boolean existsByEmail(String email) ;

}
