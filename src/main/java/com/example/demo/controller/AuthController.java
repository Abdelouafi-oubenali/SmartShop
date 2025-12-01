package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request) {

        UserDto userDto = authService.login(
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                request
        );

        if (userDto == null) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        HttpSession session = request.getSession();
        session.setAttribute("USER", userDto);
        session.setAttribute("role", userDto.getRole());
        return ResponseEntity.ok(userDto);
    }



    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {

        UserDto user = (UserDto) session.getAttribute("USER");

        if (user == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logged out successfully");
    }
}

