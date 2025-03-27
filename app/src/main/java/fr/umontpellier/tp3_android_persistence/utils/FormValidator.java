package fr.umontpellier.tp3_android_persistence.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import fr.umontpellier.tp3_android_persistence.dao.UserDAO;

public class FormValidator {

    // Vérifie que le champ n’est pas vide
    public static boolean isNotEmpty(EditText field, String errorMessage) {
        String value = field.getText().toString().trim();
        if (value.isEmpty()) {
            field.setError(errorMessage);
            return false;
        }
        return true;
    }

    // Vérifie que le login commence par une lettre et max 10 caractères
    public static boolean isValidLogin(EditText field) {
        String login = field.getText().toString().trim();
         UserDAO userDAO = new UserDAO(field.getContext());
        if (login.isEmpty()) {
            field.setError("Le login est obligatoire !");
            return false;
        } else if (!login.matches("^[a-zA-Z][a-zA-Z0-9_]{0,9}$")) {
            field.setError("Le login doit commencer par une lettre et faire au max 10 caractères.");
            return false;
        } else if (userDAO.loginExists(login)) {
            Toast.makeText(field.getContext(), "Login déjà utilisé !", Toast.LENGTH_SHORT).show();
            field.setError("Login déjà utilisé !");
            return false;
        }
        return true;
    }

    // Vérifie les noms/prénoms
    public static boolean isValidName(EditText field, String errorLabel) {
        String value = field.getText().toString().trim();
        if (value.isEmpty()) {
            field.setError(errorLabel + " est obligatoire !");
            return false;
        } else if (!value.matches("^[a-zA-ZÀ-ÿ\\s-]+$")) {
            field.setError(errorLabel + " ne doit contenir que des lettres !");
            return false;
        }
        return true;
    }

    // Vérifie la date de naissance et l’âge
    public static boolean isValidBirthdate(EditText field) {
        String value = field.getText().toString().trim();
        if (value.isEmpty()) {
            field.setError("Sélectionnez une date de naissance !");
            return false;
        }
        try {
            String[] parts = value.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1; // Java Calendar: 0-based
            int year = Integer.parseInt(parts[2]);

            Calendar birth = Calendar.getInstance();
            birth.set(year, month, day);

            Calendar today = Calendar.getInstance();
            today.add(Calendar.YEAR, -18);

            if (birth.after(today)) {
                field.setError("Vous devez avoir au moins 18 ans !");
                Toast.makeText(field.getContext(), "Vous devez avoir au moins 18 ans !", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            field.setError("Date invalide !");
            return false;
        }
        return true;
    }

    // Vérifie un numéro de téléphone
    public static boolean isValidPhone(EditText field) {
        String phone = field.getText().toString().trim();
        UserDAO userDAO = new UserDAO(field.getContext());
        if (phone.isEmpty()) {
            field.setError("Le numéro de téléphone est obligatoire !");
            return false;
        } else if (!phone.matches("^\\d{8,15}$")) {
            field.setError("Numéro invalide ! (8 à 15 chiffres)");
            return false;
        } else if (userDAO.phoneExists(phone)) {
            Toast.makeText(field.getContext(), "Le numéro de téléphone déjà utilisé !", Toast.LENGTH_SHORT).show();
            field.setError("Le numéro de téléphone utilisé !");
            return false;
        }
        return true;
    }

    // Vérifie un email
    public static boolean isValidEmail(EditText field) {
        String email = field.getText().toString().trim();
        UserDAO userDAO = new UserDAO(field.getContext());
        if (email.isEmpty()) {
            field.setError("L'email est obligatoire !");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            field.setError("Format d'email invalide !");
            return false;
        } else if (userDAO.emailExists(email)) {
            Toast.makeText(field.getContext(), "L'email déjà utilisé !", Toast.LENGTH_SHORT).show();
            field.setError("L'email  déjà utilisé !");
            return false;
        }
        return true;
    }

    // Vérifie mot de passe & confirmation
    public static boolean doPasswordsMatch(EditText pwd, EditText confirmPwd) {
        String p1 = pwd.getText().toString();
        String p2 = confirmPwd.getText().toString();

        if (p1.isEmpty()) {
            pwd.setError("Mot de passe requis !");
            return false;
        }
        if (p2.isEmpty()) {
            confirmPwd.setError("Confirmation requise !");
            return false;
        }

        if (!p1.equals(p2)) {
            confirmPwd.setError("Les mots de passe ne correspondent pas !");
            return false;
        }
        return true;
    }

    // Vérifie que l'utilisateur a coché au moins un centre d’intérêt
    public static boolean isAtLeastOneChecked(List<CheckBox> checkBoxes, Toast errorToast) {
        for (CheckBox cb : checkBoxes) {
            if (cb.isChecked()) return true;
        }
        if (errorToast != null) {
            errorToast.show();
        }
        return false;
    }
}
