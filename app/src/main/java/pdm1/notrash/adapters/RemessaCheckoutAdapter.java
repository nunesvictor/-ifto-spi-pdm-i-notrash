package pdm1.notrash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import pdm1.notrash.R;
import pdm1.notrash.entity.Pacote;
import pdm1.notrash.entity.Residuo;

public class RemessaCheckoutAdapter extends ArrayAdapter<Pacote> {
    private final List<Pacote> objects;
    private final int resource;

    public RemessaCheckoutAdapter(@NonNull Context context, int resource, @NonNull List<Pacote> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, null);
        }

        Pacote pacote = objects.get(position);

        if (pacote != null) {
            TextView nomePacoteTextView = v.findViewById(R.id.nomePacoteTextView);
            TextView conteudoPacoteTextView = v.findViewById(R.id.conteudoPacoteTextView);
            TextView associacaoPacoteTextView = v.findViewById(R.id.associacaoPacoteTextView);

            if (nomePacoteTextView != null) {
                nomePacoteTextView.setText(v.getContext().getString(
                        R.string.nome_pacote, position + 1));
            }

            if (conteudoPacoteTextView != null) {
                String conteudo = "";

                for (Residuo r : pacote.getResiduos()) {
                    if (conteudo.length() > 0) {
                        conteudo = conteudo.concat(", ");
                    }

                    conteudo = conteudo.concat(r.getDescricao());
                }

                conteudoPacoteTextView.setText(conteudo);
            }

            if (associacaoPacoteTextView != null) {
                associacaoPacoteTextView.setText(pacote.getDestinatario().getNome());
            }
        }

        return v;
    }
}
