package com.listManager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listManager.dto.UserRegistrationDto;
import com.listManager.model.User;
import com.listManager.model.UserList;
import com.listManager.service.ListManagerService;
import com.listManager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
public class ListManagerController {
    private static final Logger log = LoggerFactory.getLogger(ListManagerController.class);

    private final ListManagerService listManagerService;

    @Autowired
    public ListManagerController(ListManagerService listManagerService) {
        this.listManagerService = listManagerService;
    }

    @CrossOrigin
    @GetMapping("/get_list")
    public ResponseEntity<String> getUserList(@RequestParam int list_id) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info("list_id={}", list_id);

            UserList userList = listManagerService.getUserList(list_id);

            log.info("List was received");
            return ResponseEntity.ok(userList.toJson().toString());
        } catch (Throwable e) {
            return ResponseEntity.badRequest().body("Get user list by id error");
        }
    }

    @CrossOrigin
    @GetMapping("/get_all_user_lists")
    public ResponseEntity<String> getAllUserList(@RequestParam int user_id) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserList userList = listManagerService.getUserList(user_id);

            log.info("Lists were received");
            return ResponseEntity.ok(userList.toJson().toString());
        } catch (Throwable e) {
            return ResponseEntity.badRequest().body("Get user list by id error");
        }
    }



    @CrossOrigin
    @PostMapping("/create_list")
    public ResponseEntity<String> createUserList(HttpServletRequest request) {
        try {
            String jsonString = request.getReader().lines().collect(Collectors.joining());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(jsonString);
            int userID = json.get("user_id").asInt();
            String listName = json.get("list_name").asText();

            int listID = listManagerService.createList(userID, listName);

            return ResponseEntity.ok(Integer.toString(listID));
        } catch (Throwable e) {
            return ResponseEntity.badRequest().body("Create list error");
        }
    }

    @CrossOrigin
    @PostMapping("/update_list")
    public ResponseEntity<String> updateUserList(HttpServletRequest request) {
        try {
            String jsonString = request.getReader().lines().collect(Collectors.joining());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(jsonString);

            int listID = json.get("list_id").asInt();
            String data = json.get("row_data").asText();

            listManagerService.updateUserList(listID, data);

            return ResponseEntity.ok("Success");
        } catch (Throwable e) {
            return ResponseEntity.badRequest().body("Create list error");
        }
    }
}