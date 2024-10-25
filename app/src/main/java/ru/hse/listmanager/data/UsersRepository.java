package ru.hse.listmanager.data;

import static java.util.stream.Collectors.toCollection;

import android.util.Log;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import retrofit2.Call;
import ru.hse.listmanager.data.model.Result;
import ru.hse.listmanager.data.model.ResultHolder;
import ru.hse.listmanager.data.model.User;
import ru.hse.listmanager.network.NetworkManager;
import ru.hse.listmanager.network.authentication.LoginService;
import ru.hse.listmanager.network.authentication.model.AuthenticationResponse;
import ru.hse.listmanager.network.authentication.model.AuthorizationRequest;
import ru.hse.listmanager.network.authentication.model.RegisterRequest;
import ru.hse.listmanager.network.social.CommunicationService;

/**
 * Class that requests authentication and user information from the remote data source and maintains
 * an in-memory cache of login status and user credentials information.
 */
public class UsersRepository extends AbstractRepository {

  private static volatile UsersRepository instance;

  private final LoginService loginService;

  private final CommunicationService communicationService;

  public User user = null;

  @Getter
  private ArrayList<User> followers = new ArrayList<>(); // TODO

  @Getter
  private ArrayList<User> following = new ArrayList<>(); // TODO

  private UsersRepository() {
    super();
    this.loginService = NetworkManager.getInstance().getInstanceOfService(LoginService.class);
    this.communicationService = NetworkManager.getInstance()
        .getInstanceOfService(CommunicationService.class);

  }

  /**
   * Get instance of Login Repository.
   *
   * @return login repository.
   */
  public static UsersRepository getInstance() {
    if (instance == null) {
      instance = new UsersRepository();
    }
    return instance;
  }


  public User getLoggedUser() {
    return user;
  }


  /**
   * set logged in user.
   *
   * @param user LoggedInUser user.
   */
  private void setLoggedInUser(User user) {
    this.user = user;
  }

  /**
   * login user.
   *
   * @param username user name.
   * @param password password.
   * @return result value.
   */
  public CompletableFuture<Result<AuthenticationResponse>> login(String username, String password) {
    final ResultHolder<AuthenticationResponse> resultOfAuthorization = new ResultHolder<>();
    Call<AuthenticationResponse> loginServiceCall = loginService.login(
        new AuthorizationRequest(username, password));
    loginServiceCall.enqueue(
        getCallback(resultOfAuthorization, "Username or password are not correct"
            , (result) -> setLoggedInUser(
                new User(result.getId(), result.getHandle()))));
    return getCompletableFuture(resultOfAuthorization)
        .whenCompleteAsync((result, throwable) -> {
        });
  }

  /**
   * Sign up user.
   *
   * @param username username.
   * @param password password.
   * @param handle   handle.
   * @param name     user name.
   * @param surname  user surname.
   * @return result value.
   */
  public CompletableFuture<Result<AuthenticationResponse>> signUp(String username, String password,
      String handle,
      String name,
      String surname) {
    final ResultHolder<AuthenticationResponse> resultOfAuthorization = new ResultHolder<>();
    Call<AuthenticationResponse> loginServiceCall = loginService.register(
        new RegisterRequest(username, handle, password, name, surname));
    loginServiceCall.enqueue(
        getCallback(resultOfAuthorization, "Username is not correct or is already taken"
            , authenticationResponse -> setLoggedInUser(
                new User(authenticationResponse.getId(), authenticationResponse.getHandle()))));
    return getCompletableFuture(resultOfAuthorization).whenCompleteAsync((result, throwable) -> {
    });


  }
}