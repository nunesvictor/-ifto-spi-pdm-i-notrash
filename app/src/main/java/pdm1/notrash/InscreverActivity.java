package pdm1.notrash;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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

public class InscreverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscrever);

        EditText usernameEditText = ((TextInputLayout) findViewById(R.id.usernameTextField)).getEditText();
        EditText passwordEditText = ((TextInputLayout) findViewById(R.id.passwordTextField)).getEditText();
        EditText confirmPasswordEditText = ((TextInputLayout) findViewById(R.id.confirmPasswordTextField)).getEditText();
        Button inscreverButton = findViewById(R.id.inscreverButton);

        inscreverButton.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            assert usernameEditText != null;
            String username = usernameEditText.getText().toString();

            assert passwordEditText != null;
            String password = passwordEditText.getText().toString();

            assert confirmPasswordEditText != null;
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (password.equals(confirmPassword)) {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "notrash.db")
                        .allowMainThreadQueries()
                        .build();

                try {
                    UserDao userDao = db.userDao();

                    User user = new User();

                    user.username = username;
                    user.password = password;

                    userDao.insertAll(user);

                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } catch (SQLiteConstraintException e) {
                    Snackbar.make(v, "Ocorreu um erro na requisição", Snackbar.LENGTH_INDEFINITE).setAction(R.string.dispensar, v1 -> {
                        usernameEditText.setText("");
                        passwordEditText.setText("");
                        confirmPasswordEditText.setText("");

                        usernameEditText.requestFocus();
                    }).show();
                }
            }
        });
    }
}