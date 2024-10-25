package com.listManager.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class UserList {
    public Integer id;
    public String name;
    public List<String> rows;

    public UserList(int id, String name, List<String> rows) {
        this.id = id;
        this.name = name;
        this.rows = rows;
    }
    public JsonNode toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.createObjectNode();

        json.put("id", id);
        json.put("name", name);
        json.set("rows", rows.stream().collect(
                objectMapper::createArrayNode,
                ArrayNode::add,
                ArrayNode::addAll
        ));
        return json;
    }
}
