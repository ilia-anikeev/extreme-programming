package ru.hse.listmanager.ui.listmanager.lists.list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.List;
import ru.hse.listmanager.MainActivity;
import ru.hse.listmanager.R;
import ru.hse.listmanager.data.model.lists.UserList;
import ru.hse.listmanager.databinding.FragmentListBinding;
import ru.hse.listmanager.ui.listmanager.addlist.AddListViewModel;

/**
 * PostDetailsFragment.
 **/
public class ListDetailsFragment extends Fragment {

  @SuppressLint("SetTextI18n")
  private ListsViewModel listsViewModel;
  private UserList list;
  private FragmentListBinding binding;

  @Override
  public void onResume() {
    super.onResume();
    ((MainActivity) requireActivity()).showActionBar();
    requireActivity().findViewById(R.id.bottomToolsBar).setVisibility(View.GONE);
  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentListBinding.inflate(inflater, container, false);
    listsViewModel = new ViewModelProvider(requireActivity()).get(ListsViewModel.class);
    list = listsViewModel.getList().getValue();
    return binding.getRoot();
  }

  @SuppressLint("SetTextI18n")
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

  }
}