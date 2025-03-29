package fr.umontpellier.tp3_android_persistence;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import fr.umontpellier.tp3_android_persistence.adapters.DateAdapter;
import fr.umontpellier.tp3_android_persistence.models.DateItem;
import fr.umontpellier.tp3_android_persistence.utils.DateUtils;
import fr.umontpellier.tp3_android_persistence.utils.SessionManager;
import fr.umontpellier.tp3_android_persistence.viewmodels.PlanningViewModel;

public class PlanningSummaryActivity extends AppCompatActivity {

    private TextView tvSlot1, tvSlot2, tvSlot3, tvSlot4, tvMonthYear;
    private PlanningViewModel planningViewModel;
    private RecyclerView rvDates;
    private DateAdapter dateAdapter;

    private int currentYear, currentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_summary);

        tvSlot1 = findViewById(R.id.tvSlot1);
        tvSlot2 = findViewById(R.id.tvSlot2);
        tvSlot3 = findViewById(R.id.tvSlot3);
        tvSlot4 = findViewById(R.id.tvSlot4);
        tvMonthYear = findViewById(R.id.tvMonthYear);
        rvDates = findViewById(R.id.rvDates);

        FloatingActionButton fab = findViewById(R.id.fabAddPlanning);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlanningActivity.class);
            startActivity(intent);
        });

        ImageView btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            SessionManager.clearSession(this);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });


        // Date actuelle
        Calendar today = Calendar.getInstance();
        currentYear = today.get(Calendar.YEAR);
        currentMonth = today.get(Calendar.MONTH);
        int todayDay = today.get(Calendar.DAY_OF_MONTH);

        // Générer les jours du mois avec le jour actuel sélectionné
        List<DateItem> dateItems = DateUtils.generateDaysOfMonth(currentYear, currentMonth, todayDay);

        // Adapter avec sélection
        dateAdapter = new DateAdapter(this, dateItems, selectedDate -> loadPlanning(selectedDate));
        rvDates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvDates.setAdapter(dateAdapter);
        // Faire défiler jusqu’au jour sélectionné
        int selectedPosition = -1;
        for (int i = 0; i < dateItems.size(); i++) {
            if (dateItems.get(i).isSelected()) {
                selectedPosition = i;
                break;
            }
        }
        if (selectedPosition != -1) {
            rvDates.scrollToPosition(selectedPosition);
        }


        // ViewModel
        planningViewModel = new ViewModelProvider(this).get(PlanningViewModel.class);

        // Charger planning du jour
        String todayDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(today.getTime());
        loadPlanning(todayDate);

        // Afficher le mois/année actuel
        tvMonthYear.setText(new SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                .format(today.getTime()));

        // Flèches navigation mois
        ImageView btnPrevMonth = findViewById(R.id.btnPrevMonth);
        ImageView btnNextMonth = findViewById(R.id.btnNextMonth);

        btnPrevMonth.setOnClickListener(v -> {
            if (currentMonth == 0) {
                currentMonth = 11;
                currentYear--;
            } else {
                currentMonth--;
            }
            updateMonth();
        });

        btnNextMonth.setOnClickListener(v -> {
            if (currentMonth == 11) {
                currentMonth = 0;
                currentYear++;
            } else {
                currentMonth++;
            }
            updateMonth();
        });
    }

    private void updateMonth() {
        // Mettre à jour le texte du mois
        tvMonthYear.setText(new SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                .format(new GregorianCalendar(currentYear, currentMonth, 1).getTime()));

        List<DateItem> newDates = DateUtils.generateDaysOfMonth(currentYear, currentMonth, 1);
        dateAdapter.updateDates(newDates);

        // Faire défiler jusqu’au jour sélectionné (dans le nouveau mois)
        int selectedPosition = -1;
        for (int i = 0; i < newDates.size(); i++) {
            if (newDates.get(i).isSelected()) {
                selectedPosition = i;
                break;
            }
        }
        if (selectedPosition != -1) {
            rvDates.scrollToPosition(selectedPosition);
        }


        // Charger le planning du 1er jour du mois
        if (!newDates.isEmpty()) {
            loadPlanning(newDates.get(0).getFullDate());
        }
    }

    private void loadPlanning(String date) {
        String login = SessionManager.getLogin(this);
        if (login == null) {
            Toast.makeText(this, "Erreur session. Reconnectez-vous.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        planningViewModel.getPlanningByLoginAndDate(login, date, planning -> runOnUiThread(() -> {
            if (planning != null) {
                tvSlot1.setText("08h-10h : " + getTextOrDefault(planning.getSlot1()));
                tvSlot2.setText("10h-12h : " + getTextOrDefault(planning.getSlot2()));
                tvSlot3.setText("14h-16h : " + getTextOrDefault(planning.getSlot3()));
                tvSlot4.setText("16h-18h : " + getTextOrDefault(planning.getSlot4()));
            } else {
                tvSlot1.setText("08h-10h : Aucune activité");
                tvSlot2.setText("10h-12h : Aucune activité");
                tvSlot3.setText("14h-16h : Aucune activité");
                tvSlot4.setText("16h-18h : Aucune activité");
                Toast.makeText(this, "Aucun planning trouvé pour cette date.", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private String getTextOrDefault(String value) {
        return (value == null || value.isEmpty()) ? "Aucune activité" : value;
    }
}
