package ru.hse.listmanager.ui.listmanager.addlist;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;
import java.util.Objects;
import ru.hse.listmanager.MainActivity;
import ru.hse.listmanager.R;
import ru.hse.listmanager.data.UsersRepository;
import ru.hse.listmanager.databinding.FragmentAddListBinding;

public class AddListFragment extends Fragment {

  private AddListViewModel addListViewModel;
  private FragmentAddListBinding binding;

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
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentAddListBinding.inflate(inflater, container, false);
    addListViewModel = new ViewModelProvider(this).get(AddListViewModel.class);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    final TextView arrivalDateEditText = binding.arrivalDateEditText;
    final TextView departureDateEditText = binding.departureDateEditText;
    final Button saveButton = binding.saveButton;

    saveButton.setOnClickListener(this::saveTrip);
  }

  private void saveTrip(View v) {
//    addListViewModel.createList(binding.travelNameEditText.getText().toString(),
//        UsersRepository.getInstance().getLoggedUser());
    navigateUp();
  }

  private void navigateUp() {
    ((MainActivity) requireActivity()).getNavigationGraph().navigateUp();
  }
}