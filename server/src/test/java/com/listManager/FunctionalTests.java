package com.listManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class FunctionalTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void saveTest() {
        int userID = 1;
        String listName = "listName";
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/create_list")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createSaveRequest(userID, listName))
                .exchange()
                .expectStatus().isOk();
    }

    private ObjectNode createSaveRequest(int userID, String listName) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.createObjectNode();

        json.put("user_id", userID);
        json.put("list_name", listName);
        return json;
    }
}
