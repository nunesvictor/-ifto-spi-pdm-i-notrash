package pdm1.notrash.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import pdm1.notrash.R;
import pdm1.notrash.adapters.NovaRemessaAdapter;
import pdm1.notrash.entity.Residuo;

public class NovaRemessaFragment extends Fragment {
    private final List<Residuo> dataSet = new ArrayList<>();
    private final ArrayList<Residuo> residuos = new ArrayList<>();

    public NovaRemessaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSet.addAll(Residuo.newResiduoList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nova_remessa, container, false);

        ListView residuosListView = view.findViewById(R.id.residuosListView);
        Button criarRemessaButton = view.findViewById(R.id.criarRemessaButton);

        residuosListView.setAdapter(new NovaRemessaAdapter(
                view.getContext(), R.layout.item_nova_remessa, dataSet));

        residuosListView.setOnItemClickListener((adapterView, v, i, l) -> {
            if (residuosListView.isItemChecked(i)) {
                residuos.add((Residuo) residuosListView.getItemAtPosition(i));
            } else {
                residuos.remove((Residuo) residuosListView.getItemAtPosition(i));
            }

            criarRemessaButton.setEnabled(residuos.size() > 0);
        });

        criarRemessaButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialog_confirm_create_remessa)
                    .setPositiveButton(R.string.sim_quero_continuar, (dialog, id) -> {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("residuos", residuos);

                        getParentFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragment_container_view, RemessaCheckoutFragment.class, bundle)
                                .commit();
                    })
                    .setNegativeButton(R.string.cancelar, (dialog, id) -> {
                        // User cancelled the dialog
                    });
            builder.create().show();
        });

        return view;
    }
}
