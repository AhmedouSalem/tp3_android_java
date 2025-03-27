package fr.umontpellier.tp3_android_persistence.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import fr.umontpellier.tp3_android_persistence.Intefaces.OnDataPassListener;
import fr.umontpellier.tp3_android_persistence.R;
import fr.umontpellier.tp3_android_persistence.models.User;
import fr.umontpellier.tp3_android_persistence.utils.FormValidator;

public class FragmentInfo extends Fragment {

    private TextInputEditText etNom, etPrenom, etDateNaissance, etNumTel, etEmail;
    private MaterialCheckBox cbSport, cbMusique, cbLecture;
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
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        etNom = view.findViewById(R.id.etNom);
        etPrenom = view.findViewById(R.id.etPrenom);
        etDateNaissance = view.findViewById(R.id.etDateNaissance);
        etNumTel = view.findViewById(R.id.etNumTel);
        etEmail = view.findViewById(R.id.etEmail);
        cbSport = view.findViewById(R.id.cbSport);
        cbMusique = view.findViewById(R.id.cbMusique);
        cbLecture = view.findViewById(R.id.cbLecture);

        etDateNaissance.setOnClickListener(v -> showDatePicker());

        user = new User();

        return view;
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) - 18;
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    etDateNaissance.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        calendar.set(year - 82, Calendar.JANUARY, 1);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }


    public boolean validateForm() {
        boolean valid = true;

        String nom = Objects.requireNonNull(etNom.getText()).toString().trim();
        String prenom = Objects.requireNonNull(etPrenom.getText()).toString().trim();
        String dateNaissance = Objects.requireNonNull(etDateNaissance.getText()).toString().trim();
        String numTel = Objects.requireNonNull(etNumTel.getText()).toString().trim();
        String email = Objects.requireNonNull(etEmail.getText()).toString().trim();


        if (!FormValidator.isValidName(etNom, "Le nom")) valid = false;
        if (!FormValidator.isValidName(etPrenom, "Le prénom")) valid = false;
        if (!FormValidator.isValidBirthdate(etDateNaissance)) valid = false;
        if (!FormValidator.isValidPhone(etNumTel)) valid = false;
        if (!FormValidator.isValidEmail(etEmail)) valid = false;

        List<CheckBox> interets = Arrays.asList(cbSport, cbMusique, cbLecture);


        // Vérification des centres d’intérêt
        List<String> centresInteret = new ArrayList<>();
        if (cbSport.isChecked()) centresInteret.add("Sport");
        if (cbMusique.isChecked()) centresInteret.add("Musique");
        if (cbLecture.isChecked()) centresInteret.add("Lecture");

        if (!FormValidator.isAtLeastOneChecked(interets, Toast.makeText(getContext(), "Sélectionnez au moins un centre d’intérêt", Toast.LENGTH_SHORT))) {
            valid = false;
        }

        if (valid) {
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setDateNaissance(dateNaissance);
            user.setNumTel(numTel);
            user.setEmail(email);
            user.setCentresInteret(centresInteret);
            dataPassListener.onDataPass(user);
        }

        return valid;
    }

}
