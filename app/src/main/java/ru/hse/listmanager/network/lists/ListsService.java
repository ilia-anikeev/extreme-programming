package ru.hse.listmanager.network.lists;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.hse.listmanager.data.model.lists.UserList;
import ru.hse.listmanager.network.lists.model.AddListRequest;

public interface ListsService {

  @GET("/get_user_lists?userId={userId}")
  Call<List<UserList>> getUserLists(@Path("userId") Integer userId);

  @POST("/add_list?userId={userId}")
  Call<String> addList(@Path("userId") Integer userId, @Body AddListRequest addListRequest);

  @PUT("/update_trip?userId={userId}")
  Call<String> updateList(@Query("userId") Integer userId, @Body UserList list);
}
