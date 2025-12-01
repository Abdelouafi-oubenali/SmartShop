package com.example.demo.service;

import com.example.demo.enums.Role;
import jakarta.servlet.http.HttpServletRequest;

public interface HelperService {
    boolean isAuthenticated (HttpServletRequest request) ;

    boolean hasRole(HttpServletRequest request, Role requiredRole);
}
