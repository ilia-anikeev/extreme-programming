package com.listManager.service;


import com.listManager.model.UserList;
import com.listManager.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListManagerService {
    private final ListRepository listRepository;

    @Autowired
    public ListManagerService(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    public UserList getUserList(int listID) {
        List<String> rows = listRepository.getUserListRows(listID);
        String name = listRepository.getUserListName(listID);
        return new UserList(listID, name, rows);
    }

    public int createList(int userID, String listName) {
        return listRepository.createList(userID, listName);
    }
}
