package ru.hse.listmanager.ui.listmanager.lists.list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import lombok.Getter;
import lombok.Setter;
import ru.hse.listmanager.data.model.lists.UserList;

/**
 * ViewModel that provides trip to PostDetails and PostNotes fragments.
 */
@Getter
@Setter
public class ListsViewModel extends ViewModel {

  private MutableLiveData<UserList> list = new MutableLiveData<>();

  public void setList(UserList trip) {
    this.list = new MutableLiveData<>(trip);
  }
}
