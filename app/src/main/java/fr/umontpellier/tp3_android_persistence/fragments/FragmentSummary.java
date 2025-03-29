package fr.umontpellier.tp3_android_persistence.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.umontpellier.tp3_android_persistence.R;
import fr.umontpellier.tp3_android_persistence.models.User;
public class FragmentSummary extends Fragment {

    private TextView tvLogin, tvNomPrenom, tvDateNaissance, tvNumTel, tvEmail, tvInterets;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        tvLogin = view.findViewById(R.id.tvLogin);
        tvNomPrenom = view.findViewById(R.id.tvNomPrenom);
        tvDateNaissance = view.findViewById(R.id.tvDateNaissance);
        tvNumTel = view.findViewById(R.id.tvNumTel);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvInterets = view.findViewById(R.id.tvInterets);

        Bundle args = getArguments();
        if (args != null) {
            user = (User) args.getSerializable("user");
            if (user != null) {
                afficherInformations(user);
            }
        }

        return view;
    }

    private void afficherInformations(User user) {
        tvLogin.setText("Login : " + user.getLogin());
        tvNomPrenom.setText("Nom / Prénom : " + user.getNom() + " " + user.getPrenom());
        tvDateNaissance.setText("Date de naissance : " + user.getDateNaissance());
        tvNumTel.setText("Téléphone : " + user.getNumTel());
        tvEmail.setText("Email : " + user.getEmail());
        tvInterets.setText("Centres d’intérêt : " + (user.getCentresInteret() != null ?
                String.join(", ", user.getCentresInteret()) : "Aucun"));
    }

    public void updateUI() {
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");
            afficherInformations(user);
        }
    }
}
