package ru.hse.listmanager.data.model;

import java.io.Serializable;
import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository.
 */
@Data
@AllArgsConstructor
public class User implements Serializable {

  private int id;
  private String name;
}