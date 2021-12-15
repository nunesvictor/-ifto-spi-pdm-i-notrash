package pdm1.notrash.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pdm1.notrash.AppDatabase;
import pdm1.notrash.R;
import pdm1.notrash.adapters.RemessaCheckoutAdapter;
import pdm1.notrash.dao.RemessaDao;
import pdm1.notrash.dao.UserDao;
import pdm1.notrash.entity.Associacao;
import pdm1.notrash.entity.Pacote;
import pdm1.notrash.entity.Remessa;
import pdm1.notrash.entity.Residuo;
import pdm1.notrash.entity.User;

public class RemessaCheckoutFragment extends Fragment {
    private final List<Pacote> pacotes = new ArrayList<>();
    private final List<Associacao> associacoes = new ArrayList<>();

    public RemessaCheckoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ArrayList<Residuo> residuos = getArguments().getParcelableArrayList("residuos");

            associacoes.addAll(Associacao.newAssociacaoList());
            Collections.shuffle(associacoes);

            for (Associacao associacao : associacoes) {
                List<Residuo> residuosPacote = new ArrayList<>();

                for (Residuo residuo : associacao.getResiduosProcessados()) {
                    int index = residuos.indexOf(residuo);

                    if (index != -1) {
                        residuosPacote.add(residuos.remove(index));
                    }
                }

                if (residuosPacote.size() > 0) {
                    pacotes.add(new Pacote(associacao, residuosPacote.toArray(new Residuo[0])));
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remessa_checkout, container, false);

        ListView remessaCheckoutListView = view.findViewById(R.id.remessaCheckoutListView);
        Button solicitarRetiradaButton = view.findViewById(R.id.solicitarRetiradaButton);

        remessaCheckoutListView.setAdapter(new RemessaCheckoutAdapter(view.getContext(), R.layout.item_remessa_checkout, pacotes));

        solicitarRetiradaButton.setEnabled(pacotes.size() > 0);
        solicitarRetiradaButton.setOnClickListener(v -> {
            ArrayList<String> residuos = new ArrayList<>();

            for (Pacote pacote : pacotes) {
                for (Residuo residuo : pacote.getResiduos()) {
                    residuos.add(residuo.getDescricao());
                }
            }

            try {
                User user = getCurrentUser(v.getContext());
                createRemessa(v.getContext(), user, residuos);

                Bundle bundle = new Bundle();
                bundle.putString("message", "Nova remessa aguardando retirada.");

                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, HomeFragment.class, bundle)
                        .commit();
            } catch (SQLiteException e) {
                Snackbar.make(v, "Ocorreu um erro na requisição", Snackbar.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void createRemessa(Context context, User user, ArrayList<String> residuos) throws SQLiteException {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "notrash.db")
                .allowMainThreadQueries()
                .build();

        Remessa remessa = new Remessa();

        remessa.userId = user.uid;
        remessa.residuos = residuos;

        RemessaDao remessaDao = db.remessaDao();
        remessaDao.insertAll(remessa);
    }

    private User getCurrentUser(Context context) throws SQLiteException {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "notrash.db")
                .allowMainThreadQueries()
                .build();
        UserDao userDao = db.userDao();

        return userDao.getUser(getUserFromPreferences(context));
    }

    private String getUserFromPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "user_preferences", Context.MODE_PRIVATE);
        return preferences.getString("username", "username");
    }
}
