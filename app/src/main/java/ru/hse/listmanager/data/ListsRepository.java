package ru.hse.listmanager.data;

import androidx.annotation.NonNull;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.Getter;
import retrofit2.Call;
import ru.hse.listmanager.data.model.Result;
import ru.hse.listmanager.data.model.ResultHolder;
import ru.hse.listmanager.data.model.lists.UserList;
import ru.hse.listmanager.network.NetworkManager;
import ru.hse.listmanager.network.lists.ListsService;
import ru.hse.listmanager.network.lists.model.AddListRequest;

public class ListsRepository extends AbstractRepository {


  private static volatile ListsRepository instance;

  private final ListsService listsService;
  @Getter
  private List<UserList> userLists = new ArrayList<>();

  private ListsRepository() {
    super();
    this.listsService = NetworkManager.getInstance().getInstanceOfService(ListsService.class);
  }

  public static ListsRepository getInstance() {
    if (instance == null) {
      instance = new ListsRepository();
    }
    return instance;
  }
  public void resetLists() {
    userLists = new ArrayList<>();
  }

  public CompletableFuture<Result<List<UserList>>> getUserLists(
      Integer userId) {
    ResultHolder<List<UserList>> resultHolder = new ResultHolder<>();
    Call<List<UserList>> getUserCalls = listsService.getUserLists(userId);
    getUserCalls.enqueue(
        getCallback(resultHolder, "", (result) -> userLists = result));
    return getCompletableFuture(resultHolder);
  }

  public CompletableFuture<Result<String>> addList(Integer userId,
      AddListRequest addListRequest) {
    ResultHolder<String> resultHolder = new ResultHolder<>();
    Call<String> addListCall = listsService.addList(userId, addListRequest);
    addListCall.enqueue(getCallback(resultHolder, "User with this id not exists", (result) -> {
    }));
    return getCompletableFuture(resultHolder);
  }

  public CompletableFuture<Result<String>> updateList(UserList list) {
    ResultHolder<String> resultHolder = new ResultHolder<>();
    Call<String> updateTripCall = listsService.updateList(list.getUserId(), list);
    updateTripCall.enqueue(
        getCallback(resultHolder, "User or trip with this id not exist", (result) -> {
        }));
    return getCompletableFuture(resultHolder);
  }
}