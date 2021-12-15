package pdm1.notrash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import pdm1.notrash.R;
import pdm1.notrash.entity.Residuo;

public class NovaRemessaAdapter extends ArrayAdapter<Residuo> {
    private final List<Residuo> objects;
    private final int resource;

    public NovaRemessaAdapter(@NonNull Context context, int resource, @NonNull List<Residuo> objects) {
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

        Residuo residuo = objects.get(position);

        if (residuo != null) {
            CheckedTextView text1 = v.findViewById(R.id.text1);

            if (text1 != null) {
                text1.setText(String.format("%s\n%s", residuo.getDescricao(),
                        String.join(", ", residuo.getExemplos()).concat(", etc.")));
            }
        }

        return v;
    }
}
