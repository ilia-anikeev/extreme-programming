package com.listManager;

import com.listManager.dto.UserLoginDto;
import com.listManager.dto.UserRegistrationDto;
import com.listManager.exceptions.InvalidPassword;
import com.listManager.exceptions.UserAlreadyExist;
import com.listManager.exceptions.UserNotFoundException;
import com.listManager.model.*;
import com.listManager.repository.*;
import com.listManager.service.ListManagerService;
import com.listManager.service.UserService;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UnitTests {
    private final UserManager userManager = new UserManager();
    private final UserService userService = new UserService(userManager);
    private final ListManagerService listManagerService = new ListManagerService(new ListRepository());

    @Test
    void successfulAuthorization() {
        new DatabaseCleaner().deleteDB();
        new DatabaseInitializer().initDB();
        String username = "bebrinskiy";
        String password = "password";
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(username, password);
        UserLoginDto userLoginDto = new UserLoginDto(username, password);
        try {
            userService.registerUser(userRegistrationDto);

            User user = userService.loginUser(userLoginDto);
            userManager.deleteUser(user.getUserID());
        } catch (UserAlreadyExist | InvalidPassword | UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void invalidPassword() {
        String username = "bebrinskiy";
        String password = "password";
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(username, password);
        try {
            User user = userService.registerUser(userRegistrationDto);

            String invalidPassword = "invalid";
            UserLoginDto userLoginDto = new UserLoginDto(username, invalidPassword);
            assertThrows(InvalidPassword.class, () -> userService.loginUser(userLoginDto));
            userManager.deleteUser(user.getUserID());
        } catch (UserAlreadyExist e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createUserTest() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("bebrinskiy", "password");
        try {
            userService.registerUser(userRegistrationDto);
            User user = userManager.findByUsername("bebrinskiy");
            assertEquals("bebrinskiy", user.getUsername());
            userManager.deleteUser(user.getUserID());
        } catch (UserAlreadyExist e) {
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
        } catch (UserAlreadyExist e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveList() {
        try {
            new DatabaseInitializer().initDB();
            listManagerService.createList(1, "lst");
            listManagerService.updateUserList(1, "data");

            new DatabaseCleaner().deleteDB();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getList() {
        try {
            new DatabaseCleaner().deleteDB();
            new DatabaseInitializer().initDB();
            listManagerService.createList(1, "lst1");
            List<String> rows = new ArrayList<>();
            rows.add("dataq");
            listManagerService.updateUserList(1, rows.get(0));

            UserList list = listManagerService.getUserList(1);
            UserList expectedList = new UserList(1, "lst1", rows);
            assertEquals(list.toJson().toString(), expectedList.toJson().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveBigDataList() {
        try {
            new DatabaseCleaner().deleteDB();
            new DatabaseInitializer().initDB();
            listManagerService.createList(1, "lst");

            for (int i = 0; i < 100; ++i) {
                listManagerService.updateUserList(1, "row" + i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getBigDataList() {
        try {
            new DatabaseCleaner().deleteDB();
            new DatabaseInitializer().initDB();

            saveBigDataList();
            List<String> expectedRows = new ArrayList<>();
            for (int i = 0; i < 100; ++i) {
                expectedRows.add("row" + i);
            }

            UserList list = listManagerService.getUserList(1);

            assertEquals(list.rows.stream().collect(Collectors.toSet()), expectedRows.stream().collect(Collectors.toSet()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
