package ru.hse.listmanager.data.model.lists;

import java.util.List;

public class UserList {
  public Integer id;
  public Integer userId;
  public String name;
  public List<String> rows;

  public Integer getUserId() {
    return userId;
  }
}
