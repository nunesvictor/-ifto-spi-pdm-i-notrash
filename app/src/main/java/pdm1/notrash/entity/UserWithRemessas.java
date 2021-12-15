package pdm1.notrash.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithRemessas {
    @Embedded
    public User user;
    @Relation(parentColumn = "uid", entityColumn = "userId")
    public List<Remessa> remessas;
}
