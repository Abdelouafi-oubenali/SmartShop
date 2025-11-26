package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpSession;
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
            @RequestBody LoginRequest request,
            HttpSession session) {

        UserDto userDto = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        if (userDto == null) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        session.setAttribute("USER", userDto);

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
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }
}

