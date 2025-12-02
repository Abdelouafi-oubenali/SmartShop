package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.enums.Role;
import com.example.demo.service.HelperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class HelperServiceImpl  implements HelperService {

    @Override
    public boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("USER") != null;
    }


    @Override
    public boolean hasRole(HttpServletRequest request, Role requiredRole) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Role userRole = (Role) session.getAttribute("role");
        if (userRole == null) return false;

        return userRole == requiredRole;
    }

    @Override
    public Long getLoggedUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;

        Object userObj = session.getAttribute("USER");
        if (userObj == null) return null;

        UserDto userDto = (UserDto) userObj;
        return userDto.getId();
    }


}
