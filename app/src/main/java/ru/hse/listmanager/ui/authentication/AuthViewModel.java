package ru.hse.listmanager.ui.authentication;

import static com.google.common.hash.Hashing.sha256;

import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import lombok.Getter;
import ru.hse.listmanager.R;
import ru.hse.listmanager.data.ListsRepository;
import ru.hse.listmanager.data.UsersRepository;
import ru.hse.listmanager.data.model.Result;
import ru.hse.listmanager.data.model.User;
import ru.hse.listmanager.network.authentication.model.AuthenticationResponse;


@Getter
public class AuthViewModel extends ViewModel {

  private final static String TAG = "AuthViewModel";
  private final MutableLiveData<SignUpFormState> signUpFormState = new MutableLiveData<>();
  private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
  private final UsersRepository usersRepository = UsersRepository.getInstance();

  public void login(String username, String password) {

    String hashedPassword = String.valueOf(sha256().hashString(password, StandardCharsets.UTF_8));
    CompletableFuture<Result<AuthenticationResponse>> result = usersRepository.login(username,
        hashedPassword);
    runExecutorToWaitResult(result,
        () -> loginResult.setValue(new LoginResult(R.string.login_failed)));
  }

  private void runExecutorToWaitResult(CompletableFuture<Result<AuthenticationResponse>> future,
      Runnable troublesHandler) {
    Handler handler = new Handler(Looper.getMainLooper());
    future.whenCompleteAsync((result, throwable) -> handler.post(() -> {
      if (result.isSuccess()) {
        AuthenticationResponse response =
            ((Result.Success<AuthenticationResponse>) result).getData();
        User data = new User(response.getId(), response.getHandle());
        ListsRepository.getInstance()
            .getUserLists(usersRepository.user.getId());
        loginResult.setValue(new LoginResult(data));
      } else {
        troublesHandler.run();
      }
    }));
  }

  public void signUp(String username, String password, String name, String surname,
      String handle) {
    String hashedPassword = String.valueOf(sha256().hashString(password, StandardCharsets.UTF_8));

    CompletableFuture<Result<AuthenticationResponse>> result = usersRepository.signUp(username,
        hashedPassword, handle,
        surname, name);
    runExecutorToWaitResult(result,
        () -> loginResult.setValue(new LoginResult(R.string.login_failed)));
  }
}