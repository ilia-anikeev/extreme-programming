package ru.hse.listmanager.ui.listmanager.lists.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.Objects;
import ru.hse.listmanager.MainActivity;
import ru.hse.listmanager.R;
import ru.hse.listmanager.data.model.lists.UserList;
import ru.hse.listmanager.databinding.FragmentListBinding;
import ru.hse.listmanager.ui.listmanager.lists.ListsAdapter;

public class ListFragment extends Fragment {

  ListsViewModel postViewModel;
  FragmentListBinding binding;

  @Override
  public void onResume() {
    super.onResume();
    ((MainActivity) requireActivity()).showActionBar();
    requireActivity().findViewById(R.id.bottomToolsBar).setVisibility(View.GONE);
  }

  @Override
  public void onStop() {
    super.onStop();
    Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).hide();
    requireActivity().findViewById(R.id.bottomToolsBar).setVisibility(View.VISIBLE);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentListBinding.inflate(inflater, container, false);
    Bundle args = getArguments();
    if (args != null) {
      UserList list = (UserList) args.get(ListsAdapter.POST_ARG);
      postViewModel = new ViewModelProvider(requireActivity()).get(ListsViewModel.class);
      postViewModel.setList(list);
    }

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
  }
}