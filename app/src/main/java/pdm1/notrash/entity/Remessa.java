package pdm1.notrash.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Remessa {
    @PrimaryKey(autoGenerate = true)
    public long remessaId;
    public long userId;
    public ArrayList<String> residuos;
}
