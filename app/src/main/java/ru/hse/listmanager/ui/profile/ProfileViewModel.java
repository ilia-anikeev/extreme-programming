package ru.hse.listmanager.ui.profile;

import androidx.lifecycle.ViewModel;
import lombok.Getter;
import ru.hse.listmanager.data.UsersRepository;
import ru.hse.listmanager.data.model.User;

/**
 * ProfileViewModel that provides user from repository.
 */
@Getter
public class ProfileViewModel extends ViewModel {

  private final User user = UsersRepository.getInstance().getLoggedUser();
}