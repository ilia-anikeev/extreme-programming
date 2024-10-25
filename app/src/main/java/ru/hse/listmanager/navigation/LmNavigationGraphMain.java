package ru.hse.listmanager.navigation;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import java.io.Serializable;
import java.util.Objects;
import ru.hse.listmanager.MainActivity;
import ru.hse.listmanager.R;
import ru.hse.listmanager.data.model.lists.UserList;
import ru.hse.listmanager.databinding.ActivityMainBinding;
import ru.hse.listmanager.ui.listmanager.lists.ListsAdapter;

public class LmNavigationGraphMain extends NavHostFragment {

  private final MainActivity activity;
  private final ActivityMainBinding binding;
  private NavController navController;

  public LmNavigationGraphMain(MainActivity activity, ActivityMainBinding binding) {
    this.activity = activity;
    this.binding = binding;
    initializeNavigation();
  }

  public void initializeNavigation() {
    Toolbar actionBar = activity.findViewById(R.id.my_toolbar);
    activity.setSupportActionBar(actionBar);
    Objects.requireNonNull(activity.getSupportActionBar()).hide();

    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.feed_navigation_graph, R.id.navigation_map, R.id.navigation_places,
        R.id.profile_navigation_graph)
        .build();

    navController = Navigation.findNavController(activity, R.id.nav_host_fragment_container);
    NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
    NavigationUI.setupActionBarWithNavController(activity, navController, appBarConfiguration);
    setupButtonsClickListeners();
  }

  public void setupButtonsClickListeners() {
    binding.planTripButton.setOnClickListener(v -> navigateToAddList());
  }

  public void navigateToLogin() {
    navController.navigate(R.id.auth_navigation_graph);
  }

  public void navigateToSignUp() {
    navController.navigate(R.id.navigation_sign_up);
  }

  public void navigateToMainGraph() {
    Objects.requireNonNull(activity.getSupportActionBar()).hide();
    navController.navigate(R.id.main_navigation_graph);
  }

  public void navigateToListPage(UserList list) {
    Bundle bundle = new Bundle();
    bundle.putSerializable(ListsAdapter.POST_ARG, (Serializable) list);

    navController.navigate(R.id.navigation_post, bundle);
  }

  public void navigateUp() {
    navController.navigateUp();
  }

  public void navigateToAddList() {
    navController.navigate(R.id.navigation_plan_trip);
  }
}
