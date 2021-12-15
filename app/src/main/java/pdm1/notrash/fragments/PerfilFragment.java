package pdm1.notrash.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import pdm1.notrash.AppPreferences;
import pdm1.notrash.MainActivity;
import pdm1.notrash.R;

public class PerfilFragment extends Fragment {
    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        TextView usernameTextView = view.findViewById(R.id.usernameTextView);
        Button sairButton = view.findViewById(R.id.sairButton);

        sairButton.setOnClickListener(v -> {
            AppPreferences preferences = new AppPreferences(v.getContext());
            preferences.clear();

            startActivity(new Intent(v.getContext(), MainActivity.class));
            requireActivity().finish();
        });

        if (usernameTextView != null) {
            AppPreferences preferences = new AppPreferences(view.getContext());
            usernameTextView.setText(String.format("@%s", preferences.getCurrentUsername()));
        }

        return view;
    }
}
