package fr.umontpellier.tp3_android_persistence;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.umontpellier.tp3_android_persistence.dao.PlanningDAO;
import fr.umontpellier.tp3_android_persistence.models.Planning;
import fr.umontpellier.tp3_android_persistence.utils.SessionManager;

public class PlanningSummaryActivity extends AppCompatActivity {

    private TextView tvSlot1, tvSlot2, tvSlot3, tvSlot4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_summary);

        tvSlot1 = findViewById(R.id.tvSlot1);
        tvSlot2 = findViewById(R.id.tvSlot2);
        tvSlot3 = findViewById(R.id.tvSlot3);
        tvSlot4 = findViewById(R.id.tvSlot4);

        FloatingActionButton fab = findViewById(R.id.fabAddPlanning);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlanningActivity.class);
            startActivity(intent);
        });


        // Récupérer le login utilisateur
        String login = SessionManager.getLogin(this);
        if (login == null) {
            Toast.makeText(this, "Erreur session. Reconnectez-vous.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Récupérer le planning
        PlanningDAO dao = new PlanningDAO(this);
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        Planning planning = dao.getPlanningByLogin(login, date);

        if (planning != null) {
            tvSlot1.setText("08h-10h : " + getTextOrDefault(planning.getSlot1()));
            tvSlot2.setText("10h-12h : " + getTextOrDefault(planning.getSlot2()));
            tvSlot3.setText("14h-16h : " + getTextOrDefault(planning.getSlot3()));
            tvSlot4.setText("16h-18h : " + getTextOrDefault(planning.getSlot4()));
        } else {
            Toast.makeText(this, "Aucun planning trouvé.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getTextOrDefault(String value) {
        return (value == null || value.isEmpty()) ? "Aucune activité" : value;
    }
}
