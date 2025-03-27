package fr.umontpellier.tp3_android_persistence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import fr.umontpellier.tp3_android_persistence.utils.SessionManager;

public class WelcomeActivity extends AppCompatActivity {

    private MaterialButton btnInscription, btnConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        String login = SessionManager.getLogin(this);

        if (login != null) {
            Intent intent = new Intent(WelcomeActivity.this, PlanningSummaryActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        btnInscription = findViewById(R.id.btnInscription);
        btnConnexion = findViewById(R.id.btnConnexion);

        btnInscription.setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class))
        );

        btnConnexion.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class))
        );
    }
}
