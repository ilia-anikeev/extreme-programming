package ru.hse.listmanager.ui.authentication;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.credentials.CredentialManager;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.hse.listmanager.MainActivity;
import ru.hse.listmanager.R;
import ru.hse.listmanager.data.UsersRepository;
import ru.hse.listmanager.data.model.User;
import ru.hse.listmanager.databinding.FragmentSignUpBinding;


public class SignUpFragment extends Fragment {


  FragmentSignUpBinding binding;
  AuthViewModel authViewModel;

  CredentialManager credentialManager;
  private EditText emailEditText;
  private EditText passwordEditText;
  private EditText repeatedPasswordEditText;
  private EditText handlerEditText;
  private EditText nameEditText;
  private EditText surnameEditText;
  private ProgressBar loadingProgressBar;
  private Button signUpButton;
  private View goToLoginButton;

  @Override
  public void onResume() {
    super.onResume();
    requireActivity().findViewById(R.id.bottomToolsBar).setVisibility(View.GONE);
  }

  @Override
  public void onStop() {
    super.onStop();
    requireActivity().findViewById(R.id.bottomToolsBar).setVisibility(View.VISIBLE);
  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentSignUpBinding.inflate(inflater, container, false);
    authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    credentialManager = CredentialManager.create(requireContext());
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    emailEditText = binding.editTextName;
    nameEditText = binding.editTextName;
    passwordEditText = binding.editTextPassword;
    goToLoginButton = binding.goToLogIn;
    loadingProgressBar = binding.loading;
    signUpButton = binding.signUpButton;
    repeatedPasswordEditText = binding.editTextRepeatedPassword;
    signUpButton.setEnabled(false);

    authViewModel.getSignUpFormState()
        .observe(getViewLifecycleOwner(), this::updateUIWithSignUpFormState);
    authViewModel.getLoginResult().
        observe(getViewLifecycleOwner(), this::updateUIWithSignUpResult);

    setButtonListeners();
  }


  public void updateUIWithSignUpFormState(SignUpFormState signUpFormState) {
    if (signUpFormState == null) {
      return;
    }
    signUpButton.setEnabled(signUpFormState.isDataValid());
    if (signUpFormState.getUsernameError() != null) {
      emailEditText.setError(getString(signUpFormState.getUsernameError()));
    } else if (signUpFormState.getPasswordError() != null) {
      passwordEditText.setError(getString(signUpFormState.getPasswordError()));
    } else if (signUpFormState.getRepeatedPasswordError() != null) {
      repeatedPasswordEditText.setError(getString(signUpFormState.getRepeatedPasswordError()));
    } else if (signUpFormState.getHandlerError() != null) {
      handlerEditText.setError(getString(signUpFormState.getHandlerError()));
    }
  }

  public void updateUIWithSignUpResult(LoginResult loginResult) {
    if (loginResult == null) {
      return;
    }
    Handler handler = new Handler();
    handler.postDelayed(() -> {
      if (loginResult.getError() != null) {
        showSignUpFailed(loginResult.getError());
      } else if (loginResult.getSuccess() != null) {
        signUpSuccessful(loginResult.getSuccess());
      }
    }, 1000);
  }

  private void setButtonListeners() {
    goToLoginButton.setOnClickListener(this::navigateToLogin);

    signUpButton.setOnClickListener(v -> {
      signUp();
    });

  }

  /**
   * Navigate to main screen.
   */
  private void signUpSuccessful(User user) {
    User loggedUser = UsersRepository.getInstance().getLoggedUser();
    Log.d("SignUpFragment", loggedUser.getName());
    ((MainActivity) requireActivity()).getNavigationGraph().navigateToMainGraph();
  }

  private void showLoadingView() {
    signUpButton.setVisibility(View.GONE);
    loadingProgressBar.setVisibility(View.VISIBLE);
  }

  private void signUp() {
    authViewModel.signUp(emailEditText.getText().toString(),
        passwordEditText.getText().toString(),
        surnameEditText.getText().toString(),
        nameEditText.getText().toString(),
        handlerEditText.getText().toString());
  }

  private void showSignUpFailed(@StringRes Integer errorString) {
    if (getContext() != null && getContext().getApplicationContext() != null) {
      Toast.makeText(
          getContext().getApplicationContext(),
          errorString,
          Toast.LENGTH_LONG).show();
    }
  }

  private void navigateToLogin(View v) {
    ((MainActivity) requireActivity()).getNavigationGraph().navigateUp();
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}