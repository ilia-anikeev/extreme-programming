package ru.hse.listmanager.ui.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.hse.listmanager.data.UsersRepository;
import ru.hse.listmanager.data.model.User;
import ru.hse.listmanager.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

  UsersRepository repository = UsersRepository.getInstance();
  @SuppressWarnings("FieldCanBeLocal")
  private ProfileViewModel profileViewModel;
  private FragmentProfileBinding binding;
  private User user;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    profileViewModel =
        new ViewModelProvider(this).get(ProfileViewModel.class);
    binding = FragmentProfileBinding.inflate(inflater, container, false);
    user = profileViewModel.getUser();
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setUserInfo();
  }

  @SuppressLint("SetTextI18n")
  private void setUserInfo() {
    binding.fullnameView.setText(user.getName());
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

}