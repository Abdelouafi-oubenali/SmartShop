package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDto login(String username, String password, HttpServletRequest request) {

        User user = userRepository.findByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());

        return userMapper.toDto(user);
    }
}
