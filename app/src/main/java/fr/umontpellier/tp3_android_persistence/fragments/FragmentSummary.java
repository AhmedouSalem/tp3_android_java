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

    private TextView tvSummary;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        tvSummary = view.findViewById(R.id.tvSummary);

        // Récupérer le Bundle
        Bundle args = getArguments();
        if (args != null) {
            user = (User) args.getSerializable("user");
            if (user != null) {
                afficherInformations(user);
                Log.d("FragmentSummary", "Données reçues : " + user.toString());
            } else {
                Log.e("FragmentSummary", "L'objet User est null");
            }
        } else {
            Log.e("FragmentSummary", "args is null - Les données n'ont pas été transmises");
        }

        return view;
    }

    private void afficherInformations(User user) {
        String infos = "Login : " + user.getLogin() + "\n"
                + "Nom : " + user.getNom() + "\n"
                + "Prénom : " + user.getPrenom() + "\n"
                + "Date de naissance : " + user.getDateNaissance() + "\n"
                + "Téléphone : " + user.getNumTel() + "\n"
                + "Email : " + user.getEmail() + "\n"
                + "Centres d'intérêt : " + (user.getCentresInteret() != null ? String.join(", ", user.getCentresInteret()) : "Aucun");

        tvSummary.setText(infos);
    }

    public void updateUI() {
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");
            afficherInformations(user);
        }
    }

}
