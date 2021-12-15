package pdm1.notrash.entity;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Associacao implements Parcelable {
    private final String nome;
    private final String endereco;
    private final String telefone;
    private final Residuo[] residuosProcessados;

    public Associacao(String nome, String endereco, String telefone, Residuo[] residuosProcessados) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.residuosProcessados = residuosProcessados;
    }

    protected Associacao(Parcel in) {
        nome = in.readString();
        endereco = in.readString();
        telefone = in.readString();
        residuosProcessados = in.createTypedArray(Residuo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(endereco);
        dest.writeString(telefone);
        dest.writeTypedArray(residuosProcessados, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Associacao> CREATOR = new Creator<Associacao>() {
        @Override
        public Associacao createFromParcel(Parcel in) {
            return new Associacao(in);
        }

        @Override
        public Associacao[] newArray(int size) {
            return new Associacao[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public Residuo[] getResiduosProcessados() {
        return residuosProcessados;
    }

    @SuppressLint("DefaultLocale")
    public static List<Associacao> newAssociacaoList() {
        List<Associacao> associacoes = new ArrayList<>();
        List<Residuo> residuos = Residuo.newResiduoList();

        while (associacoes.size() < 10) {
            int id = associacoes.size() + 1;
            int[] range = new int[2];

            for (int i = 0; i < 2; i++) {
                range[i] = ThreadLocalRandom.current().nextInt(0, residuos.size());
                Arrays.sort(range);
            }

            associacoes.add(new Associacao(
                    String.format("Associação %d", id),
                    String.format("Rua %d, nº %d", id, id),
                    String.format("(63) 9999-999%d", id),
                    residuos.subList(range[0], range[1]).toArray(new Residuo[0])
            ));
        }

        return associacoes;
    }

    @NonNull
    @Override
    public String toString() {
        return nome;
    }
}
