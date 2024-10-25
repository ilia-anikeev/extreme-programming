package com.listManager.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listManager.dto.UserLoginDto;
import com.listManager.dto.UserRegistrationDto;

import com.listManager.exceptions.InvalidPassword;
import com.listManager.exceptions.UserAlreadyExist;
import com.listManager.exceptions.InvalidEmail;
import com.listManager.exceptions.UserNotFoundException;
import com.listManager.model.User;


import com.listManager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class AuthorizationController {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationController.class);

    private final UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }


    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(HttpServletRequest request) {
        try {
            String jsonString = request.getReader().lines().collect(Collectors.joining());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(jsonString);
            UserRegistrationDto registrationDto = new UserRegistrationDto(json);
            User user = userService.registerUser(registrationDto);
            return ResponseEntity.ok(Integer.toString(user.getUserID()));
        } catch (UserAlreadyExist | InvalidEmail e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Throwable e) {
            log.error("Server error", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(HttpServletRequest request) {
        try {
            String jsonString = request.getReader().lines().collect(Collectors.joining());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(jsonString);
            UserLoginDto loginDto = new UserLoginDto(json);
            User user = userService.loginUser(loginDto);
            return ResponseEntity.ok(Integer.toString(user.getUserID()));
        } catch (UserNotFoundException | InvalidPassword e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Throwable e) {
            log.error("Server error", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}