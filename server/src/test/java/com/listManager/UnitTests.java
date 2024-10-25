package com.listManager;

import com.listManager.dto.UserRegistrationDto;
import com.listManager.exceptions.UserAlreadyExist;
import com.listManager.exceptions.InvalidEmail;
import com.listManager.model.*;
import com.listManager.repository.*;
import com.listManager.service.UserService;
import jdk.jfr.DataAmount;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UnitTests {
    private final UserManager userManager = new UserManager();
    private final UserService userService = new UserService(userManager);

    @Test
    void createUserTest() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("bebrinskiy", "bebra@example.com", "password");
        try {
            userService.registerUser(userRegistrationDto);
            User user = userManager.findUserByEmail("bebra@example.com");
            assertEquals("bebrinskiy", user.getUsername());
            assertEquals("bebra@example.com", user.getEmail());
            userManager.deleteUser(user.getUserID());
        } catch (UserAlreadyExist | InvalidEmail e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void badEmail() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("bebrinskiy", "bebraexample.com", "password");
        assertThrows(InvalidEmail.class, () -> userService.registerUser(userRegistrationDto));
    }

    @Test
    void createUserTwice() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("usertwice", "usertwice@example.com", "password");
        try {
            User user = userService.registerUser(userRegistrationDto);
            assertThrows(UserAlreadyExist.class, () -> userService.registerUser(userRegistrationDto));
            userManager.deleteUser(user.getUserID());
        } catch (UserAlreadyExist | InvalidEmail e) {
            throw new RuntimeException(e);
        }
    }
}