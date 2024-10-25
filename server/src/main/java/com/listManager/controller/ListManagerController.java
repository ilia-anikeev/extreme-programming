package com.listManager.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listManager.model.UserList;
import com.listManager.service.ListManagerService;
import com.listManager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}