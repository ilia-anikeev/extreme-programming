package ru.hse.listmanager.ui.listmanager.lists;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import ru.hse.listmanager.MainActivity;
import ru.hse.listmanager.data.ListsRepository;
import ru.hse.listmanager.data.UsersRepository;
import ru.hse.listmanager.data.model.Result;
import ru.hse.listmanager.data.model.User;
import ru.hse.listmanager.data.model.lists.UserList;
import ru.hse.listmanager.databinding.ItemPostTripBinding;
import ru.hse.listmanager.ui.listmanager.lists.ListsViewHolders.FeedPostViewHolder;

/**
 * FeedAdapter provide a binding from posts set to views that are displayed within a RecyclerView.
 */
public class ListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
    View.OnClickListener {

  public static final String POST_ARG = "post";
  private static final int VIEW_TYPE_ITEM = 0;
  private static final int VIEW_TYPE_LOADING = 1;
  private static final String TAG = "FEED_ADAPTER";
  @Getter
  List<UserList> items = Collections.emptyList();

  @SuppressLint("NotifyDataSetChanged")
  public void setItems(List<UserList> newItems) {
    items = newItems;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemPostTripBinding binding = ItemPostTripBinding.inflate(
        LayoutInflater.from(parent.getContext()), parent, false);
    return new FeedPostViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    showPostView((FeedPostViewHolder) viewHolder, position);
  }

  private void showPostView(FeedPostViewHolder viewHolder, int position) {
    UserList list = items.get(position);
    viewHolder.itemView.setOnClickListener(this);
    viewHolder.itemView.setTag(list);
    ItemPostTripBinding binding = viewHolder.getBinding();
    setListInfo(list, binding);
  }

  private void setListInfo(UserList list, ItemPostTripBinding binding) {
//    binding.titleText.setText(list.getName());
//    binding.profileNameText.setText(trip.getDisplayName());
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  @Override
  public void onClick(View v) {
    UserList listClicked = (UserList) v.getTag();

    MainActivity activity = (MainActivity) v.getContext();
    activity.getNavigationGraph().navigateToListPage(listClicked);
  }

  @Override
  public int getItemViewType(int position) {
    return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
  }
}
