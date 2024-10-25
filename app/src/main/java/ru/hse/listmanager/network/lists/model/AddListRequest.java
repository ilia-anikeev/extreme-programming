package ru.hse.listmanager.network.lists.model;

import java.io.Serializable;
public class AddListRequest implements Serializable {
  String name;
  Integer userId;
  public AddListRequest(Integer userId, String name) {
    this.userId = userId;
    this.name = name;
  }
}