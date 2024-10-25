package ru.hse.listmanager.ui.listmanager.lists;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.Getter;
import ru.hse.listmanager.data.ListsRepository;
import ru.hse.listmanager.data.model.Result;
import ru.hse.listmanager.data.model.lists.UserList;

/**
 * ViewModel that provides posts to Feed.
 */
@Getter
public class ListsViewModel extends ViewModel {

  private final ListsRepository listsRepository = ListsRepository.getInstance();
  @Getter
  private List<UserList> lists;

  public ListsViewModel() {
    lists = new ArrayList<>();
  }

  public List<UserList> getLists() {
    return lists;
  }

  public void getUserLists(Integer userId) {
    CompletableFuture<Result<List<UserList>>> future =
        listsRepository.getUserLists(userId);
    Log.d(ListsViewModel.class.getName(),
        "Completable future is accepted");
    try {
      future.whenCompleteAsync((result, exception) -> {
        Log.d(ListsViewModel.class.getName(),
            "Async handling of result is happening: " + result + " exception: " + exception);
        if (result.isSuccess()) {
          lists = ((Result.Success<List<UserList>>) result)
              .getData();
        }
      }).get();
    } catch (InterruptedException e) {
      Log.e(this.getClass().getName(), "InterruptedException happened in trips getting: " + e);
    } catch (ExecutionException e) {
      Log.e(this.getClass().getName(), "ExecutionException happened in trips getting: " + e);
    }
  }
}