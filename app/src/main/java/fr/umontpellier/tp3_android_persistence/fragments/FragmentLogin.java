package fr.umontpellier.tp3_android_persistence.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import fr.umontpellier.tp3_android_persistence.Intefaces.OnDataPassListener;
import fr.umontpellier.tp3_android_persistence.R;
import fr.umontpellier.tp3_android_persistence.SignUpActivity;
import fr.umontpellier.tp3_android_persistence.models.User;
import fr.umontpellier.tp3_android_persistence.utils.FormValidator;

public class FragmentLogin extends Fragment {

    private TextInputEditText etLogin, etPassword, etConfirmPassword;
    private OnDataPassListener dataPassListener;
    private User user;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDataPassListener) {
            dataPassListener = (OnDataPassListener) context;
        } else {
            throw new RuntimeException(context.toString() + " doit implémenter OnDataPassListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etLogin = view.findViewById(R.id.etLogin);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);

        user = new User();

        return view;
    }

    public boolean validateForm() {
        String login = Objects.requireNonNull(etLogin.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString();

        boolean valid = true;
        if (!FormValidator.isValidLogin(etLogin)) valid = false;
        if (!FormValidator.doPasswordsMatch(etPassword, etConfirmPassword)) valid = false;


        user.setLogin(login);
        user.setPassword(password);

        // Envoyer les données à MainActivity via l'interface
        dataPassListener.onDataPass(user);

        return valid;
    }
}
