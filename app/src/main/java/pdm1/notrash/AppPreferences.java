package pdm1.notrash;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import pdm1.notrash.entity.User;
import pdm1.notrash.entity.UserWithRemessas;

public class AppPreferences {
    private final Context context;
    private final SharedPreferences preferences;

    public AppPreferences(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
    }

    public User getCurrentUser() {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "notrash.db")
                .allowMainThreadQueries()
                .build();

        return db.userDao().getUser(getCurrentUsername());
    }

    public UserWithRemessas getCurrentUserWithRemessas() {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "notrash.db")
                .allowMainThreadQueries()
                .build();

        return db.userDao().getUserWithRemessas(getCurrentUsername());
    }

    public String getCurrentUsername() {
        return preferences.getString("username", null);
    }

    public void putCurrentUsername(String username) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("username", username);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
        editor.apply();
    }
}
