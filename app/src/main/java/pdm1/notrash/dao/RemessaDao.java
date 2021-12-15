package pdm1.notrash.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import pdm1.notrash.entity.Remessa;
import pdm1.notrash.entity.UserWithRemessas;

@Dao
public interface RemessaDao {
    @Query("SELECT * FROM remessa")
    List<Remessa> getAll();

    @Query("SELECT * FROM remessa WHERE remessaId IN (:remessaIds)")
    List<Remessa> loadAllByIds(int[] remessaIds);

    @Insert
    void insertAll(Remessa... users);

    @Delete
    void delete(Remessa Remessa);
}
