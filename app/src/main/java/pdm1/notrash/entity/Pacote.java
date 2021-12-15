package pdm1.notrash.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class Pacote implements Parcelable {
    private final Associacao destinatario;
    private final Residuo[] residuos;

    public Pacote(Associacao destinatario, Residuo[] residuos) {
        this.destinatario = destinatario;
        this.residuos = residuos;
    }

    protected Pacote(Parcel in) {
        destinatario = in.readParcelable(Associacao.class.getClassLoader());
        residuos = in.createTypedArray(Residuo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(destinatario, flags);
        dest.writeTypedArray(residuos, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pacote> CREATOR = new Creator<Pacote>() {
        @Override
        public Pacote createFromParcel(Parcel in) {
            return new Pacote(in);
        }

        @Override
        public Pacote[] newArray(int size) {
            return new Pacote[size];
        }
    };

    public Associacao getDestinatario() {
        return destinatario;
    }

    public Residuo[] getResiduos() {
        return residuos;
    }

    @NonNull
    @Override
    public String toString() {
        return "Pacote{" +
                "destinatario=" + destinatario +
                ", residuos=" + Arrays.toString(residuos) +
                '}';
    }
}
