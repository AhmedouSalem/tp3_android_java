package fr.umontpellier.tp3_android_persistence;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import fr.umontpellier.tp3_android_persistence.models.Planning;
import fr.umontpellier.tp3_android_persistence.utils.SessionManager;
import fr.umontpellier.tp3_android_persistence.viewmodels.PlanningViewModel;

public class PlanningActivity extends AppCompatActivity {

    private TextInputEditText etSlot1, etSlot2, etSlot3, etSlot4, etPlanningDate;
    private MaterialButton btnSavePlanning;
    private String currentLogin;
    private PlanningViewModel planningViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        currentLogin = SessionManager.getLogin(this);

        if (currentLogin == null) {
            Toast.makeText(this, "Erreur : utilisateur non connecté", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        planningViewModel = new ViewModelProvider(this).get(PlanningViewModel.class);

        etSlot1 = findViewById(R.id.etSlot1);
        etSlot2 = findViewById(R.id.etSlot2);
        etSlot3 = findViewById(R.id.etSlot3);
        etSlot4 = findViewById(R.id.etSlot4);
        etPlanningDate = findViewById(R.id.etPlanningDate);
        btnSavePlanning = findViewById(R.id.btnSavePlanning);

        btnSavePlanning.setOnClickListener(v -> {
            String s1 = etSlot1.getText().toString().trim();
            String s2 = etSlot2.getText().toString().trim();
            String s3 = etSlot3.getText().toString().trim();
            String s4 = etSlot4.getText().toString().trim();
            String date = etPlanningDate.getText().toString().trim();

            if (s1.isEmpty() && s2.isEmpty() && s3.isEmpty() && s4.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir au moins un créneau", Toast.LENGTH_SHORT).show();
                return;
            }

            if (date.isEmpty()) {
                Toast.makeText(this, "Veuillez sélectionner une date", Toast.LENGTH_SHORT).show();
                return;
            }

            Planning planning = new Planning(currentLogin, s1, s2, s3, s4, date);

            planningViewModel.planningExists(currentLogin, date, exists -> {
                if (exists) {
                    planningViewModel.updatePlanning(planning, success -> runOnUiThread(() -> {
                        if (success) {
                            Toast.makeText(this, "Planning mis à jour !", Toast.LENGTH_SHORT).show();
                            goToSummary();
                        } else {
                            Toast.makeText(this, "Erreur de mise à jour", Toast.LENGTH_SHORT).show();
                        }
                    }));
                } else {
                    planningViewModel.insertPlanning(planning, success -> runOnUiThread(() -> {
                        if (success) {
                            Toast.makeText(this, "Planning enregistré", Toast.LENGTH_SHORT).show();
                            goToSummary();
                        } else {
                            Toast.makeText(this, "Erreur lors de la sauvegarde", Toast.LENGTH_SHORT).show();
                        }
                    }));
                }
            });
        });

        etPlanningDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, y, m, d) -> {
                        String date = String.format("%02d/%02d/%04d", d, m + 1, y);
                        etPlanningDate.setText(date);
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void goToSummary() {
        Intent intent = new Intent(this, PlanningSummaryActivity.class);
        startActivity(intent);
        finish();
    }
}
