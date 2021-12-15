package pdm1.notrash.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pdm1.notrash.AppPreferences;
import pdm1.notrash.R;
import pdm1.notrash.adapters.RecyclerViewAdapter;
import pdm1.notrash.entity.UserWithRemessas;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppPreferences preferences = new AppPreferences(inflater.getContext());
        UserWithRemessas userWithRemessas = preferences.getCurrentUserWithRemessas();

        if (userWithRemessas != null && userWithRemessas.remessas.size() > 0) {
            View view = inflater.inflate(R.layout.fragment_home_remessas, container, false);

            RecyclerView recyclerView = view.findViewById(R.id.fragment_home_rv);
            LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new RecyclerViewAdapter(userWithRemessas.remessas));

            return view;
        }

        return inflater.inflate(R.layout.fragment_home_empty, container, false);
    }
}
