package ru.hse.listmanager.network.authentication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.hse.listmanager.network.authentication.model.AuthenticationResponse;
import ru.hse.listmanager.network.authentication.model.AuthorizationRequest;
import ru.hse.listmanager.network.authentication.model.RegisterRequest;

public interface LoginService {

  @POST("/login")
  Call<AuthenticationResponse> login(@Body AuthorizationRequest request);

  @POST("/register")
  Call<AuthenticationResponse> register(@Body RegisterRequest request);
}
