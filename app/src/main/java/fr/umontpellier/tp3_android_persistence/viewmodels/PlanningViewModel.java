package fr.umontpellier.tp3_android_persistence.viewmodels;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import fr.umontpellier.tp3_android_persistence.dao.PlanningRepository;
import fr.umontpellier.tp3_android_persistence.models.Planning;

public class PlanningViewModel extends AndroidViewModel {

    private final PlanningRepository planningRepository;

    public PlanningViewModel(@NonNull Application application) {
        super(application);
        planningRepository = new PlanningRepository(application);
    }

    public void insertPlanning(Planning planning, PlanningRepository.Callback callback) {
        planningRepository.insertPlanning(planning, callback);
    }

    public void updatePlanning(Planning planning, PlanningRepository.Callback callback) {
        planningRepository.updatePlanning(planning, callback);
    }

    public void planningExists(String login, String date, PlanningRepository.ExistsCallback callback) {
        planningRepository.planningExists(login, date, callback);
    }

    public void getPlanningByLoginAndDate(String login, String date, PlanningRepository.PlanningCallback callback) {
        planningRepository.getPlanningByLoginAndDate(login, date, callback);
    }
}
