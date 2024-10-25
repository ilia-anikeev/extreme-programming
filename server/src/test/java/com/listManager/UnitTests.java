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
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("bebrinskiy", "password");
        try {
            userService.registerUser(userRegistrationDto);
            User user = userManager.findByUsername("bebrinskiy");
            assertEquals("bebrinskiy", user.getUsername());
            userManager.deleteUser(user.getUserID());
        } catch (UserAlreadyExist | InvalidEmail e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createUserTwice() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("usertwice", "password");
        try {
            User user = userService.registerUser(userRegistrationDto);
            assertThrows(UserAlreadyExist.class, () -> userService.registerUser(userRegistrationDto));
            userManager.deleteUser(user.getUserID());
        } catch (UserAlreadyExist | InvalidEmail e) {
            throw new RuntimeException(e);
        }
    }
}