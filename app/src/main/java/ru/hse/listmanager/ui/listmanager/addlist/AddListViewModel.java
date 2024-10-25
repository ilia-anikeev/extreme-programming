package ru.hse.listmanager.ui.listmanager.addlist;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import lombok.Getter;
import ru.hse.listmanager.data.ListsRepository;
import ru.hse.listmanager.data.UsersRepository;
import ru.hse.listmanager.data.model.User;
import ru.hse.listmanager.network.lists.model.AddListRequest;

public class AddListViewModel extends ViewModel {

  private final ListsRepository listsRepository = ListsRepository.getInstance();
  @Getter
  private final MutableLiveData<AddListFormState> planTripFormState = new MutableLiveData<>();
  public final User user = UsersRepository.getInstance().user;

  public void createList(String name, List<String> rows) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
      Log.d(this.getClass().getName(), "Trip addition started to happen.");
      listsRepository.addList(
              UsersRepository.getInstance().user.getId(),
              new AddListRequest(UsersRepository.getInstance().user.getId(),name))
          .whenCompleteAsync((result, throwable) -> Log.d(this.getClass().getSimpleName(),
              "Trip is planning, userId is: " + UsersRepository.getInstance().user.getId()))
          .thenRunAsync(() -> listsRepository.getUserLists(user.getId()));
      Log.d(this.getClass().getName(), "Trip addition ended.");
    });
    executorService.shutdown();
  }

}