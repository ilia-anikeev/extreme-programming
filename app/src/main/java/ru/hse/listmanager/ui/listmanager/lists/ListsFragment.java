package ru.hse.listmanager.ui.listmanager.lists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import ru.hse.listmanager.MainActivity;
import ru.hse.listmanager.data.UsersRepository;
import ru.hse.listmanager.databinding.FragmentFeedBinding;

public class ListsFragment extends Fragment {

  private ru.hse.listmanager.ui.listmanager.lists.ListsViewModel listsViewModel;
  private FragmentFeedBinding binding;

  private ListsRecyclerViewHolder feedRecyclerViewHolder;

  @Override
  public void onResume() {
    super.onResume();
    feedRecyclerViewHolder.refreshFeed(false);

    Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).hide();
  }

  @Override
  public void onStop() {
    super.onStop();
    Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).hide();
  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    listsViewModel =
        new ViewModelProvider(this).get(ListsViewModel.class);
    binding = FragmentFeedBinding.inflate(inflater, container, false);
    feedRecyclerViewHolder = new ListsRecyclerViewHolder(binding.recyclerView);
    return binding.getRoot();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private class ListsRecyclerViewHolder {

    private final RecyclerView listsRecyclerView;
    private ListsAdapter listsAdapter;
    private LinearLayoutManager listsLayoutManager;

    public ListsRecyclerViewHolder(RecyclerView recyclerView) {
      listsRecyclerView = recyclerView;

      initializeLayoutManager();
      initializeAdapter();

      if (listsAdapter.getItemCount() == 0) {
        binding.emptyList.setVisibility(View.VISIBLE);
      } else {
        binding.emptyList.setVisibility(View.GONE);
      }
    }

    private void initializeLayoutManager() {
      listsLayoutManager = new LinearLayoutManager(getActivity());
      listsRecyclerView.setLayoutManager(listsLayoutManager);
    }

    private void initializeAdapter() {
      listsAdapter = new ListsAdapter();
      listsAdapter.setItems(listsViewModel.getLists());
      listsRecyclerView.setAdapter(listsAdapter);
    }

    private void refreshFeed(boolean needToLoadNextPosts) {
      ExecutorService executor = Executors.newCachedThreadPool();
      executor.execute(() -> {
        listsViewModel.getUserLists(UsersRepository.getInstance().user.getId());
        listsRecyclerView.postDelayed(() -> {
              loadData();
            }, 1000
        );
      });
      executor.shutdown();
      if (listsAdapter.getItemCount() == 0) {
        binding.emptyList.setVisibility(View.VISIBLE);
      } else {
        binding.emptyList.setVisibility(View.GONE);
      }
    }

    public void loadData() {
      listsAdapter.setItems(
          listsViewModel.getLists());
    }
  }
}
