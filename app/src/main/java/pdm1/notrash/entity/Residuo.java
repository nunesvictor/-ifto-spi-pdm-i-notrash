package pdm1.notrash.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Residuo implements Parcelable {
    private final String descricao;
    private final ArrayList<String> exemplos;
    private final ArrayList<String> trivias;

    public Residuo(String descricao, ArrayList<String> exemplos, ArrayList<String> trivias) {
        this.descricao = descricao;
        this.exemplos = exemplos;
        this.trivias = trivias;
    }

    protected Residuo(Parcel in) {
        descricao = in.readString();
        exemplos = in.createStringArrayList();
        trivias = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descricao);
        dest.writeStringList(exemplos);
        dest.writeStringList(trivias);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Residuo> CREATOR = new Creator<Residuo>() {
        @Override
        public Residuo createFromParcel(Parcel in) {
            return new Residuo(in);
        }

        @Override
        public Residuo[] newArray(int size) {
            return new Residuo[size];
        }
    };

    public String getDescricao() {
        return descricao;
    }

    public ArrayList<String> getExemplos() {
        return exemplos;
    }

    public ArrayList<String> getTrivias() {
        return trivias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Residuo residuo = (Residuo) o;

        return descricao.equals(residuo.descricao);
    }

    @Override
    public int hashCode() {
        int result = descricao != null ? descricao.hashCode() : 0;
        result = 31 * result + (exemplos != null ? exemplos.hashCode() : 0);
        result = 31 * result + (trivias != null ? trivias.hashCode() : 0);
        return result;
    }

    public static List<Residuo> newResiduoList() {
        return new ArrayList<>(Arrays.asList(
                new Residuo(
                        "Borracha",
                        new ArrayList<>(Arrays.asList(
                                "pneus", "câmaras de ar", "polias")),
                        new ArrayList<>(Arrays.asList(
                                "Reciclar reduz o custo final da borracha em mais de 50%",
                                "75% de energia é economizada com reciclagem da borracha"))),
                new Residuo(
                        "Metal",
                        new ArrayList<>(Arrays.asList(
                                "latas", "garrafas", "panelas")),
                        new ArrayList<>(Arrays.asList(
                                "O alumínio na natureza requer de 100 a 500 anos de decomposição",
                                "A cada kg de alumínio reciclado evita-se a mineração de 5kg de bauxita",
                                "Até 95% de energia é poupada ao reciclar uma lata de alumínio",
                                "Uma lata reciclada economiza o mesmo que 20h de uso de uma lâmpada de 100 watts"
                        ))),
                new Residuo(
                        "Papel",
                        new ArrayList<>(Arrays.asList(
                                "jornais", "livros", "revistas")),
                        new ArrayList<>(Arrays.asList(
                                "Uma folha de papel A4 exige 20 litros de água para ser produzida",
                                "Cada tonelada de papel exige o abate de até 20 árvores"))),
                new Residuo(
                        "Plástico",
                        new ArrayList<>(Arrays.asList(
                                "sacolas", "copos", "embalagens")),
                        new ArrayList<>(Arrays.asList(
                                "O reaproveitamento do plástico reciclado é de 100%",
                                "A reciclagem do plástico economiza até 90% de energia",
                                "100 toneladas de plástico reciclado evitam a extração de 1 tonelada de petróleo"))),
                new Residuo(
                        "Vidro",
                        new ArrayList<>(Arrays.asList("copos", "garrafas", "cacos")),
                        new ArrayList<>(Arrays.asList(
                                "46% das embalagens de vidro são recicladas no Brasil",
                                "Para cada 10% reciclado economiza-se 4% da energia na produção",
                                "9,5% no consumo de água é economizado com a reciclagem")))
        ));
    }
}
