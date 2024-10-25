package ru.hse.listmanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import java.util.Objects;
import lombok.Getter;
import ru.hse.listmanager.databinding.ActivityMainBinding;
import ru.hse.listmanager.navigation.LmNavigationGraphMain;
import ru.hse.listmanager.network.NetworkManager;

/**
 * Main Activity of app.
 */
@Getter
public class MainActivity extends AppCompatActivity {

  private LmNavigationGraphMain navigationGraph;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FirebaseApp.initializeApp(this);
    String urlApi = getResources().getString(R.string.URL_API);
    NetworkManager.setBaseUrl(urlApi);
    ru.hse.listmanager.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(
        getLayoutInflater());
    setContentView(binding.getRoot());
    navigationGraph = new LmNavigationGraphMain(this, binding);

    navigationGraph.navigateToLogin();
  }

  @Override
  public boolean onSupportNavigateUp() {
    navigationGraph.navigateUp();
    return true;
  }

  public void showActionBar() {
    Objects.requireNonNull(getSupportActionBar()).show();
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
  }

}