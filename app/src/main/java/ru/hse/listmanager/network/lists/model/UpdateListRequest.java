package ru.hse.listmanager.network.lists.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UpdateListRequest implements Serializable {
    public Integer listId;
    public Integer userId;
    public List<String> rows;
}
