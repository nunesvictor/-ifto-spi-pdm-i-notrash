package pdm1.notrash.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import pdm1.notrash.entity.User;
import pdm1.notrash.entity.UserWithRemessas;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE username=:username")
    User getUser(String username);

    @Transaction
    @Query("SELECT * FROM user WHERE username=:username")
    UserWithRemessas getUserWithRemessas(String username);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Transaction
    @Query("SELECT * FROM user")
    public List<UserWithRemessas> getUsersWithRemessas();

    @Query("SELECT * FROM user WHERE username=:username AND password=:password LIMIT 1")
    User login(String username, String password);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
