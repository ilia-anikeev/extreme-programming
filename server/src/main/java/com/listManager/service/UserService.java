package com.listManager.service;

import com.listManager.dto.UserLoginDto;
import com.listManager.dto.UserRegistrationDto;

import com.listManager.exceptions.InvalidPassword;
import com.listManager.exceptions.UserAlreadyExist;
import com.listManager.exceptions.UserNotFoundException;
import com.listManager.model.User;

import com.listManager.repository.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    private final UserManager userManager;

    @Autowired
    public UserService(UserManager userManager) {
        this.userManager = userManager;
    }

    public User registerUser(UserRegistrationDto registrationDto) throws UserAlreadyExist {
        if (userManager.findByUsername(registrationDto.getUsername()) != null) {
            throw new UserAlreadyExist("Username already in use");
        }

        User user = new User(registrationDto.getUsername(),
                hashPassword(registrationDto.getPassword()));
        userManager.registerUser(user);
        return user;
    }

    public User loginUser(UserLoginDto loginDto) throws UserNotFoundException, InvalidPassword {
        User user = userManager.findByUsername(loginDto.getUsername());
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if (!user.getPassword().equals(hashPassword(loginDto.getPassword()))) {
            throw new InvalidPassword("Invalid password");
        }
        return user;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(String username) {
        return userManager.findByUsername(username);
    }
}