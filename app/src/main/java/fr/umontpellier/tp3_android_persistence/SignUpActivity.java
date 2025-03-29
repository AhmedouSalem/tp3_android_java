package fr.umontpellier.tp3_android_persistence;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import fr.umontpellier.tp3_android_persistence.Intefaces.OnDataPassListener;
import fr.umontpellier.tp3_android_persistence.adapters.ViewPagerAdapter;
import fr.umontpellier.tp3_android_persistence.fragments.FragmentInfo;
import fr.umontpellier.tp3_android_persistence.fragments.FragmentLogin;
import fr.umontpellier.tp3_android_persistence.fragments.FragmentSummary;
import fr.umontpellier.tp3_android_persistence.models.User;
import fr.umontpellier.tp3_android_persistence.utils.SwipeControlTouchListener;
import fr.umontpellier.tp3_android_persistence.viewmodels.UserViewModel;

public class SignUpActivity extends AppCompatActivity implements OnDataPassListener {

    private ViewPager2 viewPager;
    private LinearProgressIndicator progressIndicator;
    private MaterialButton btnNext;
    private User user = new User();
    private Bundle userBundle = new Bundle();
    private ViewPagerAdapter adapter;
    private boolean isUserSaved = false;
    private TextView tvLogin;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        viewPager = findViewById(R.id.viewPager);
        progressIndicator = findViewById(R.id.progressIndicator);
        btnNext = findViewById(R.id.btnNext);
        tvLogin = findViewById(R.id.tvLogin);

        adapter = new ViewPagerAdapter(this, userBundle);
        viewPager.setAdapter(adapter);


        RecyclerView recyclerView = (RecyclerView) viewPager.getChildAt(0);
        recyclerView.addOnItemTouchListener(new SwipeControlTouchListener(this));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0 || position == 2) {
                    viewPager.setUserInputEnabled(false);
                } else {
                    viewPager.setUserInputEnabled(true);
                }
                updateProgress(position * 50);
                btnNext.setText("Suivant");
            }
        });

        progressIndicator.setMax(100);
        updateProgress(0);

        btnNext.setOnClickListener(v -> navigateToNextFragment());
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });



        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public void onDataPass(User userData) {
        if (userData == null) {
            Log.e("MainActivity", "onDataPass() : userData est null !");
            return;
        }

        if (userData.getLogin() != null) user.setLogin(userData.getLogin());
        if (userData.getPassword() != null) user.setPassword(userData.getPassword());
        if (userData.getNom() != null) user.setNom(userData.getNom());
        if (userData.getPrenom() != null) user.setPrenom(userData.getPrenom());
        if (userData.getDateNaissance() != null) user.setDateNaissance(userData.getDateNaissance());
        if (userData.getNumTel() != null) user.setNumTel(userData.getNumTel());
        if (userData.getEmail() != null) user.setEmail(userData.getEmail());
        if (userData.getCentresInteret() != null) user.setCentresInteret(userData.getCentresInteret());

        Log.d("MainActivity", "Données mises à jour après FragmentInfo : " + user.toString());

        // Mettre à jour le Bundle
        userBundle.putSerializable("user", user);
    }


    private void navigateToNextFragment() {
        int currentItem = viewPager.getCurrentItem();

        if (currentItem == 0) {
            FragmentLogin fragment = (FragmentLogin) getSupportFragmentManager().findFragmentByTag("f0");
            if (fragment != null && fragment.validateForm()) {
                userBundle.putSerializable("user", user);
                Log.d("SignUpActivity", "Données mises à jour après FragmentLogin : " + user.toString());

                userViewModel.loginExists(user.getLogin(), exists -> {
                    runOnUiThread(() -> {
                        if (exists) {
                            fragment.getEtLogin().setError("Login déjà utilisé !");
                            Toast.makeText(this, "Login déjà utilisé !", Toast.LENGTH_SHORT).show();
                        } else {
                            viewPager.setCurrentItem(currentItem + 1);
                            updateProgress(50);
                        }
                    });
                });

            }

        } else if (currentItem == 1) {
            FragmentInfo fragment = (FragmentInfo) getSupportFragmentManager().findFragmentByTag("f1");
            if (fragment != null && fragment.validateForm()) {

                userBundle.putSerializable("user", user);
                Log.d("SignUpActivity", "Données envoyées à FragmentSummary : " + user.toString());

                userViewModel.emailExists(user.getEmail(), emailExists -> {
                    if (emailExists) {
                        runOnUiThread(() -> {
                            fragment.getEtEmail().setError("Email déjà utilisé !");
                            Toast.makeText(this, "Email déjà utilisé !", Toast.LENGTH_SHORT).show();
                        });

                    } else {
                        userViewModel.phoneExists(user.getNumTel(), phoneExists -> {
                            if (phoneExists) {
                                runOnUiThread(() -> {
                                    fragment.getEtNumTel().setError("Numéro de téléphone déjà utilisé !");
                                    Toast.makeText(this, "Numéro de téléphone déjà utilisé !", Toast.LENGTH_SHORT).show();
                                });
                            } else {
                                userViewModel.insertUser(user, inserted -> runOnUiThread(() -> {
                                    if (inserted) {
                                        isUserSaved = true;
                                        Toast.makeText(this, "Inscription réussie !", Toast.LENGTH_LONG).show();

                                        FragmentSummary fragmentSummary = (FragmentSummary) getSupportFragmentManager().findFragmentByTag("f2");
                                        if (fragmentSummary != null) {
                                            fragmentSummary.setArguments(userBundle);
                                            fragmentSummary.updateUI();
                                        }

                                        viewPager.setCurrentItem(currentItem + 1);
                                        btnNext.setText("Terminer");
                                        updateProgress(100);
                                    } else {
                                        Toast.makeText(this, "Erreur lors de l'enregistrement !", Toast.LENGTH_SHORT).show();
                                    }
                                }));
                            }
                        });
                    }
                });
            }
        }
        else if (currentItem == 2 && isUserSaved) {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    private void updateProgress(int progress) {
        progressIndicator.setProgress(progress);
    }
}
