package fr.umontpellier.tp3_android_persistence.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class FormValidator {

    public static boolean isNotEmpty(EditText field, String errorMessage) {
        String value = field.getText().toString().trim();
        if (value.isEmpty()) {
            field.setError(errorMessage);
            return false;
        }
        return true;
    }

    public static boolean isValidLoginFormat(EditText field) {
        String login = field.getText().toString().trim();
        if (login.isEmpty()) {
            field.setError("Le login est obligatoire !");
            return false;
        } else if (!login.matches("^[a-zA-Z][a-zA-Z0-9_]{0,9}$")) {
            field.setError("Le login doit commencer par une lettre et faire au max 10 caractères.");
            return false;
        }
        return true;
    }

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

    public static boolean isValidBirthdate(EditText field) {
        String value = field.getText().toString().trim();
        if (value.isEmpty()) {
            field.setError("Sélectionnez une date de naissance !");
            return false;
        }
        try {
            String[] parts = value.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1;
            int year = Integer.parseInt(parts[2]);

            Calendar birth = Calendar.getInstance();
            birth.set(year, month, day);

            Calendar today = Calendar.getInstance();
            today.add(Calendar.YEAR, -18);

            if (birth.after(today)) {
                field.setError("Vous devez avoir au moins 18 ans !");
                return false;
            }
        } catch (Exception e) {
            field.setError("Date invalide !");
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneFormat(EditText field) {
        String phone = field.getText().toString().trim();
        if (phone.isEmpty()) {
            field.setError("Le numéro de téléphone est obligatoire !");
            return false;
        } else if (!phone.matches("^\\d{8,15}$")) {
            field.setError("Numéro invalide ! (8 à 15 chiffres)");
            return false;
        }
        return true;
    }

    public static boolean isValidEmailFormat(EditText field) {
        String email = field.getText().toString().trim();
        if (email.isEmpty()) {
            field.setError("L'email est obligatoire !");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            field.setError("Format d'email invalide !");
            return false;
        }
        return true;
    }

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
