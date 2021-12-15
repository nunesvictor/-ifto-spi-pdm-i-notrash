package pdm1.notrash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pdm1.notrash.fragments.HomeFragment;
import pdm1.notrash.fragments.NovaRemessaFragment;
import pdm1.notrash.fragments.PerfilFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppPreferences preferences = new AppPreferences(getApplicationContext());
        String username = preferences.getCurrentUsername();

        if (username == null) {
            mostrarLogin();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Bundle bundle = new Bundle();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true);

            if (item.getItemId() == R.id.homePageItem) {
                transaction = transaction.replace(R.id.fragment_container_view,
                        HomeFragment.class, bundle);
            } else if (item.getItemId() == R.id.novaRemessaPageItem) {
                transaction = transaction.replace(R.id.fragment_container_view,
                        NovaRemessaFragment.class, bundle);
            } else if (item.getItemId() == R.id.perfilPageItem) {
                transaction = transaction.replace(R.id.fragment_container_view,
                        PerfilFragment.class, bundle);
            }

            transaction.commit();

            return true;
        });
    }

    private void mostrarLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }
}
