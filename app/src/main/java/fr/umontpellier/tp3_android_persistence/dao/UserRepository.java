package fr.umontpellier.tp3_android_persistence.dao;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.umontpellier.tp3_android_persistence.database.AppDatabase;
import fr.umontpellier.tp3_android_persistence.models.User;

public class UserRepository {

    private final UserDao userDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.userDao = db.userDao();
    }

    // Insertion asynchrone
    public void insertUser(User user, Callback callback) {
            executor.execute(() -> {
                try {
                    userDao.insert(user);
                    callback.onComplete(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onComplete(false);
                }
            });

    }

    // Authentification
    public void getUserByLoginAndPassword(String login, String password, UserCallback callback) {
        executor.execute(() -> {
            User user = userDao.getUserByLoginAndPassword(login, password);
            callback.onUserLoaded(user);
        });
    }

    public void getUserByEmailAndPassword(String email, String password, UserCallback callback) {
        executor.execute(() -> {
            User user = userDao.getUserByEmailAndPassword(email, password);
            callback.onUserLoaded(user);
        });
    }

    public void loginExists(String login, ExistsCallback callback) {
        executor.execute(() -> {
            boolean exists = userDao.loginExists(login);
            callback.onResult(exists);
        });
    }

    public void emailExists(String email, ExistsCallback callback) {
        executor.execute(() -> {
            boolean exists = userDao.emailExists(email);
            callback.onResult(exists);
        });
    }

    public void phoneExists(String phone, ExistsCallback callback) {
        executor.execute(() -> {
            boolean exists = userDao.phoneExists(phone);
            callback.onResult(exists);
        });
    }

    // Callbacks
    public interface Callback {
        void onComplete(boolean success);
    }

    public interface UserCallback {
        void onUserLoaded(User user);
    }

    public interface ExistsCallback {
        void onResult(boolean exists);
    }
}
