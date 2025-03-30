package fr.umontpellier.tp3_android_persistence;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import fr.umontpellier.tp3_android_persistence.utils.SessionManager;
import fr.umontpellier.tp3_android_persistence.viewmodels.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etLogin, etPassword;
    private MaterialButton btnLogin;
    private TextView tvSignUp;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        btnLogin.setOnClickListener(v -> {
            String login = etLogin.getText().toString().trim();
            String password = etPassword.getText().toString();

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
            } else {
                // Authentification par login
                userViewModel.getUserByLoginAndPassword(login, password, user -> {
                    runOnUiThread(() -> {
                        if (user != null) {
                            Toast.makeText(this, "Bienvenue " + user.getPrenom(), Toast.LENGTH_LONG).show();
                            SessionManager.saveLogin(this, user.getLogin());
                            startActivity(new Intent(this, PlanningSummaryActivity.class));
                        } else {
                            // Authentification par email
                            userViewModel.getUserByEmailAndPassword(login, password, userByEmail -> {
                                runOnUiThread(() -> {
                                    if (userByEmail != null) {
                                        Toast.makeText(this, "Bienvenue " + userByEmail.getPrenom(), Toast.LENGTH_LONG).show();
                                        SessionManager.saveLogin(this, userByEmail.getLogin());
                                        startActivity(new Intent(this, PlanningSummaryActivity.class));
                                    } else {
                                        showLoginError();
                                    }
                                });
                            });
                        }
                    });
                });
            }
        });

        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }

    private void showLoginError() {
        new AlertDialog.Builder(this)
                .setTitle("Erreur de connexion")
                .setMessage("Login ou mot de passe incorrect.")
                .setPositiveButton("OK", null)
                .show();
    }
}
