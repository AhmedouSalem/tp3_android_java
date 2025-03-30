package fr.umontpellier.tp3_android_persistence.dao;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.umontpellier.tp3_android_persistence.database.AppDatabase;
import fr.umontpellier.tp3_android_persistence.models.Planning;

public class PlanningRepository {

    private final PlanningDao planningDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public PlanningRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.planningDao = db.planningDao();
    }

    public void insertPlanning(Planning planning, Callback callback) {
        executor.execute(() -> {
            try {
                planningDao.insert(planning);
                callback.onComplete(true);
            } catch (Exception e) {
                e.printStackTrace();
                callback.onComplete(false);
            }
        });
    }

    public void updatePlanning(Planning planning, Callback callback) {
        executor.execute(() -> {
            try {
                planningDao.update(planning);
                callback.onComplete(true);
            } catch (Exception e) {
                e.printStackTrace();
                callback.onComplete(false);
            }
        });
    }

    public void planningExists(String login, String date, ExistsCallback callback) {
        executor.execute(() -> {
            boolean exists = planningDao.planningExists(login, date);
            callback.onResult(exists);
        });
    }

    public void getPlanningByLoginAndDate(String login, String date, PlanningCallback callback) {
        executor.execute(() -> {
            Planning planning = planningDao.getPlanningByLoginAndDate(login, date);
            callback.onPlanningLoaded(planning);
        });
    }


    // Callbacks
    public interface Callback {
        void onComplete(boolean success);
    }

    public interface ExistsCallback {
        void onResult(boolean exists);
    }

    public interface PlanningCallback {
        void onPlanningLoaded(Planning planning);
    }
}
