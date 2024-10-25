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
import ru.hse.listmanager.databinding.FragmentLoginBinding;

/**
 * LoginFragment.
 */
public class LoginFragment extends Fragment {

  CredentialManager credentialManager;
  private AuthViewModel authViewModel;
  private FragmentLoginBinding binding;
  private EditText nameEditText;
  private EditText passwordEditText;
  private Button loginButton;

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


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = FragmentLoginBinding.inflate(inflater, container, false);
    credentialManager = CredentialManager.create(requireContext());
    authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    nameEditText = binding.editTextEmail;
    passwordEditText = binding.editTextPassword;
    loginButton = binding.logInButton;
    loginButton.setEnabled(false);

    authViewModel
        .getLoginResult().
        observe(getViewLifecycleOwner(), this::updateUiWithLoginResult);

    setButtonListeners();
  }

  private void updateUiWithLoginResult(LoginResult loginResult) {
    if (loginResult == null) {
      return;
    }
    loginSuccessful(loginResult.getSuccess());
  }

  public void setButtonListeners() {
    loginButton.setOnClickListener(v -> {
      login();
    });
    binding.goToSignUp.setOnClickListener(v -> navigateToSignUp());
  }

  private void loginSuccessful(User user) {
    User loggedUser = UsersRepository.getInstance().getLoggedUser();
    Log.d("LoginFragment", loggedUser.getName());
    ((MainActivity) requireActivity()).getNavigationGraph().navigateToMainGraph();
  }

  private void login() {
    authViewModel.login(nameEditText.getText().toString(),
        passwordEditText.getText().toString());
  }

  private void navigateToSignUp() {
    ((MainActivity) requireActivity()).getNavigationGraph().navigateToSignUp();
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}