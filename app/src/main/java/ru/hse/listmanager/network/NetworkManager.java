package ru.hse.listmanager.network;

import lombok.Setter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Singleton class which saves basic settings about interaction with server API.
 */
public class NetworkManager {

  private static volatile NetworkManager instance;
  @Setter
  static private String baseUrl;
  private final Retrofit retrofit;

  private NetworkManager() {
    retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(JacksonConverterFactory.create())
        .build();
  }

  /**
   * Return instance of Network Manager.
   *
   * @return instance of Network manager.
   */
  public static NetworkManager getInstance() {
    if (instance == null) {
      instance = new NetworkManager();
    }
    return instance;
  }

  /**
   * Creates instance of service.
   *
   * @param type interface of retrofit service to create.
   * @param <T>  type of interface.
   * @return instance of interface.
   */
  public <T> T getInstanceOfService(Class<T> type) {
    return retrofit.create(type);
  }
}
