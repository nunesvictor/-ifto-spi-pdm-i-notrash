package pdm1.notrash;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import pdm1.notrash.converters.RoomTypeConverters;
import pdm1.notrash.dao.RemessaDao;
import pdm1.notrash.dao.UserDao;
import pdm1.notrash.entity.Remessa;
import pdm1.notrash.entity.User;

@Database(entities = {User.class, Remessa.class}, version = 1)
@TypeConverters({RoomTypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract RemessaDao remessaDao();
}
