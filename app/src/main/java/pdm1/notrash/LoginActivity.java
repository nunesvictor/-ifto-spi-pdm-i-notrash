package pdm1.notrash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import pdm1.notrash.dao.UserDao;
import pdm1.notrash.entity.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "notrash.db")
                .allowMainThreadQueries()
                .build();

        EditText usernameEditText = ((TextInputLayout) findViewById(R.id.usernameTextField)).getEditText();
        EditText passwordEditText = ((TextInputLayout) findViewById(R.id.passwordTextField)).getEditText();
        Button loginButton = findViewById(R.id.loginButton);
        Button novaInscricaoButton = findViewById(R.id.novaInscricaoButton);

        loginButton.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            assert usernameEditText != null;
            String username = usernameEditText.getText().toString();

            assert passwordEditText != null;
            String password = passwordEditText.getText().toString();

            UserDao userDao = db.userDao();
            User user = userDao.login(username, password);

            if (user != null) {
                AppPreferences preferences = new AppPreferences(v.getContext());
                preferences.putCurrentUsername(user.username);

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("isLoggedIn", true);

                startActivity(intent);
                finish();
            } else {
                Snackbar.make(v, R.string.login_senha_incorretos, Snackbar.LENGTH_INDEFINITE).setAction(R.string.dispensar, v1 -> {
                    usernameEditText.setText("");
                    passwordEditText.setText("");
                    usernameEditText.requestFocus();
                }).show();
            }
        });

        novaInscricaoButton.setOnClickListener(v -> {
            startActivity(new Intent(this, InscreverActivity.class));
            finish();
        });
    }
}
